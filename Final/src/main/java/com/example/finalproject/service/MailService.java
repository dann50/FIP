package com.example.finalproject.service;

import com.example.finalproject.entity.Ticket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final URL resource = MailService.class.getResource("/templates/logo.png");

    public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendConfirmationEmail(Ticket ticket) throws MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
            mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("contextPath", ServletUriComponentsBuilder.fromCurrentContextPath().build().toString());
        context.setVariable("ticketId", ticket.getId());
        context.setVariable("name", ticket.getPassengerName());
        context.setVariable("seatNumber", ticket.getSeatNumber());
        context.setVariable("fromStation", ticket.getFrom());
        context.setVariable("toStation", ticket.getTo());
        context.setVariable("departure", ticket.getDeparture());
        context.setVariable("arrival", ticket.getArrival());

        helper.setFrom("support-fip@example.com");
        helper.setTo(ticket.getPassengerEmail());
        helper.setSubject("Ticket Reservation Confirmation");
        if (resource == null)
            throw new MessagingException("Resource not found");

        Path pdfPath = Files.createTempFile("pdffile", ".pdf");
        String template = templateEngine.process("ticket_confirmation_mail", context);

        File logo = new File(resource.getFile());
        generatePDF(template, pdfPath, logo.toPath());

        helper.setText(template, true);
        helper.addAttachment("ticket.pdf", pdfPath.toFile());
        helper.addInline("logo.png", logo);

        mailSender.send(mimeMessage);
    }

    private void generatePDF(String html, Path pdfPath, Path logoPath) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        // change the src of the image from cid:logo.png to the local logo path.
        // convert to URI to add the "file:///" prefix and ensure proper formatting
        String cleaned = html.replace("cid:", logoPath.getParent().toUri().toString());
        renderer.setDocumentFromString(cleaned);
        renderer.layout();
        renderer.createPDF(Files.newOutputStream(pdfPath));
    }

}
