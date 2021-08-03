package com.artatech.bilerman.Services;

import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class ImageCompressor {

    private String extension = "jpg";

    public static final int WIDTH = 800;

    public static final float COMPRESSION = 0.80f;

    private final File source;

    public ImageCompressor(File source) {
        this.source = Objects.requireNonNull(source);
        String fileName = source.getName();
        this.extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public void compressTo(File target) throws IOException {
        FileImageOutputStream targetOutputStream = new FileImageOutputStream(target);
        BufferedImage resizedImage = resize(source);

        ImageWriter writer = getWriter();
        ImageWriteParam writerSettings = getWriterSettings(writer);

        try {
            writer.setOutput(targetOutputStream);
            writer.write(null, new IIOImage(resizedImage, null, null), writerSettings);
        } finally {
            writer.dispose();
            targetOutputStream.close();
            resizedImage.flush();
        }
    }

    private BufferedImage resize(File imageFile) throws IOException {
        BufferedImage sourceImage = ImageIO.read(imageFile);

        return Scalr.resize(sourceImage, WIDTH);
    }

    private ImageWriter getWriter() {
        Iterator<ImageWriter> imageWritersIterator =
                ImageIO.getImageWritersByFormatName(extension);

        if (!imageWritersIterator.hasNext()) {
            throw new NoSuchElementException(
                    String.format("Could not find an image writer for %s format", extension));
        }

        return imageWritersIterator.next();
    }

    private ImageWriteParam getWriterSettings(ImageWriter imageWriter) {
        ImageWriteParam imageWriteParams = imageWriter.getDefaultWriteParam();

        imageWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParams.setCompressionQuality(COMPRESSION);

        return imageWriteParams;
    }
}
