package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class AuthenticationService {

    static final Long EXPIRATION_TIME = 864_000_00L;
    static final String SIGNING_KEY = "bilerman_singing_key";
    static final String BEARER_PREFIX = "Bearer";

    @Value("${app.url}")
    private String APPLICATION_URL;

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    static public void addJWTToken(HttpServletResponse response, String username){
        String jwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();

        response.addHeader("Authorization", BEARER_PREFIX + " " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    static public Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token.replace(BEARER_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if(user == null) throw new RuntimeException("Authentication failed");

            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }

        return null;
    }

    public void sendEmailVerificationEmail(User user, Locale locale){
        try {
            String token = UUID.randomUUID().toString();
            service.createVerificationToken(user, token);

            String confirmationUrl = APPLICATION_URL + "/auth/confirm/" + token;
            InputStream input = getClass().getResourceAsStream("/templates/email.html");
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
            String html = doc.html();
            html = html.replace("{{url}}", APPLICATION_URL);
            html = html.replace("{{header}}", "Аккаунтуңузду улаңыз");
            html = html.replace("{{greeting}}", "Урматтуу " + user.getName() + ",");
            html = html.replace("{{top}}", "Биз менен жазып баштаганыңызга абдан кубанычтабыз! Биздин тиркемебизди колдонуп баштоо үчүн биринчиден, сиздин аккаунту ырастоо керек. Ал үчүн төмөндөгү баскычты басыңыз.");
            html = html.replace("{{copyPasteLink}}", "Эгерде жогорудагы баскыч иштебей калса, анда төмөнкү шилтемени копиялап, браузериңизге коюп издеңиз");
            html = html.replace("{{bottom}}", "Эгерде бул кат сизге адашып келген болсо, жөн эле өчүрүп салсаңыз болот");
            html = html.replace("{{button_label}}", "Ырастоо");
            html = html.replace("{{action_url}}", confirmationUrl);
            String message = html;

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Билерман аккаунтуңузду ырастаңыз");
                messageHelper.setText(message, true);
            };
            mailSender.send(messagePreparator);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void sendPasswordResetEmail(User user, Locale locale){
        try {
            String token = UUID.randomUUID().toString();
            service.createPasswordResetToken(user, token);

            String resetURL = APPLICATION_URL + "/auth/password/reset/" + token;
            InputStream input = getClass().getResourceAsStream("/templates/email.html");
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
            String html = doc.html();
            html = html.replace("{{url}}", APPLICATION_URL);
            html = html.replace("{{header}}", "Сыр сөздү жаңылоо");
            html = html.replace("{{greeting}}", "Урматтуу " + user.getName() + ",");
            html = html.replace("{{top}}", "Bilerman сыр сөзүңүздү унутуп койдуңуз окшойт. Эгер бул чын болсо, сыр сөзүңүздү жаңыдан баштоо үчүн төмөнкүнү басыңыз.");
            html = html.replace("{{bottom}}", "Эгерде бул кат сизге адашып келген болсо, жөн эле өчүрүп салсаңыз болот");
            html = html.replace("{{button_label}}", "Сыр сөздү жаңылоо");
            html = html.replace("{{copyPasteLink}}", "Эгерде жогорудагы баскыч иштебей калса, анда төмөнкү шилтемени копиялап, браузериңизге коюп издеңиз");
            html = html.replace("{{action_url}}", resetURL);
            String message = html;

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Билерман сыр сөзүңүздү жаңылаңыз");
                messageHelper.setText(message, true);
            };
            mailSender.send(messagePreparator);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
