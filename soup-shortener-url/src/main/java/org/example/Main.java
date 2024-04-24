package org.example;

import org.example.controller.AuthorizeController;
import org.example.controller.LinkController;
import org.example.controller.dto.LinkDto;
import org.example.controller.dto.UserDto;
import org.example.jdbc.JdbcUtils;
import org.example.repository.Impl.AuthorizeRepositoryImpl;
import org.example.repository.Impl.LinkRepositoryImpl;
import org.example.service.Impl.AuthorizeServiceImpl;
import org.example.service.Impl.LinkServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkController linkController = new LinkController(new LinkServiceImpl(new LinkRepositoryImpl()));
        AuthorizeController authorizeController = new AuthorizeController(new AuthorizeServiceImpl(new AuthorizeRepositoryImpl()));

        System.out.println(JdbcUtils.createConnection());
        Scanner scanner = new Scanner(System.in);
        String user = null;

        while (true) {
            if (user == null) {
                System.out.print("""
                        SOUP SHORTENER URL!!!
                        Выберете действие:
                        1) Войти
                        2) Зарегистрироваться
                        0) Выйти
                        """);
                int action = scanner.nextInt();
                scanner.nextLine();
                switch (action) {
                    case 1: {
                        System.out.println("Введите логин:");
                        String login = scanner.nextLine();
                        try {
                            authorizeController.login(new UserDto(login));
                            user = login;
                        } catch (Exception ex) {
                            System.out.println("EXCEPTION!!!");
                            String message = ex.toString();
                            System.out.println(message);
                            System.out.println("Введите любую строку:");
                            scanner.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите логин:");
                        String login = scanner.nextLine();
                        try {
                            authorizeController.register(new UserDto(login));
                        } catch (Exception ex) {
                            System.out.println("EXCEPTION!!!");
                            String message = ex.toString();
                            System.out.println(message);
                            System.out.println("Введите любую строку:");
                            scanner.nextLine();
                        }
                        break;
                    }
                    case 0:
                        JdbcUtils.closeConnection();
                        return;
                }
            } else {
                System.out.print("""
                        SOUP SHORTENER URL!!!
                        Выберете действие:
                        1) Сократить ссылку
                        2) Получить ссылку
                        3) Получить все ссылки
                        0) Выйти
                        """);
                int action = scanner.nextInt();
                scanner.nextLine();
                switch (action) {
                    case 1: {
                        System.out.println("Введите ссылку:");
                        String link = scanner.nextLine();
                        try {
                            LinkDto linkDto = linkController.addLink(new UserDto(user), new LinkDto(link, ""));
                            System.out.println("Короткая ссылка: " + linkDto.shortLink());
                        } catch (Exception ex) {
                            System.out.println("EXCEPTION!!!");
                            String message = ex.toString();
                            System.out.println(message);
                            System.out.println("Введите любую строку:");
                            scanner.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите короткую ссылку:");
                        String link = scanner.nextLine();
                        try {
                            LinkDto longlinkDto = linkController.getLink(new LinkDto(null, link));
                            System.out.println("Ссылка: " + longlinkDto.longLink());
                        } catch (Exception ex) {
                            System.out.println("EXCEPTION!!!");
                            String message = ex.toString();
                            System.out.println(message);
                            System.out.println("Введите любую строку:");
                            scanner.nextLine();
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Ссылки:");
                        List<LinkDto> list = linkController.getUserLinks(new UserDto(user));
                        for (var p : list)
                            System.out.println(p.shortLink() + " - " + p.longLink());
                        break;
                    }
                    case 0:
                        user = null;
                }
            }
        }
    }
}