package com.example.projectswp.repository.util;

import com.example.projectswp.service.impl.Gmail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Util {
    public static Date getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return new Date(dtf.format(now));
    }

    public static int getUserId(){
        int userId = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userId = Integer.parseInt(authentication.getCredentials().toString());
        return userId;
    }
    public static String getUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void sendMail(String subject, String message, String email) throws GeneralSecurityException, IOException, MessagingException {
        Gmail gmail = new Gmail();
        gmail.sendMessage(subject, message, email);
    }

    public static <T> List<T> getSubListByPage(List<T> list, int pageNumber, int pageSize) {
        if(list == null || pageNumber < 1 || pageSize < 1)
            throw new IllegalArgumentException("Invalid input parameters.");

        int startIndex = (pageNumber- 1) * pageSize;
        if(startIndex >= list.size()) {
            return Collections.emptyList();
        }
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}