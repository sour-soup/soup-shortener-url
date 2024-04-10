package org.example;

import org.example.controller.LinkController;
import org.example.controller.dto.LongLinkDto;
import org.example.controller.dto.ShortLinkDto;
import org.example.repository.LinkRepositoryImpl;
import org.example.service.LinkServiceImpl;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkController linkController = new LinkController(new LinkServiceImpl(new LinkRepositoryImpl()));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    SOUP SHORTENER URL!!!
                    Выберете действие:
                    1) Сократить ссылку
                    2) Получить ссылку
                    0) Выйти
                    """);
            int action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 1: {
                    System.out.println("Введите ссылку:");
                    String link = scanner.nextLine();
                    try {
                        ShortLinkDto shortLinkDto = linkController.addLink(new LongLinkDto(link));
                        System.out.println("Короткая ссылка: " + shortLinkDto.link());
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
                        LongLinkDto longLinkDto = linkController.getLink(new ShortLinkDto(link));
                        System.out.println("Ссылка: " + longLinkDto.link());
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
                    return;
            }
        }
    }
}