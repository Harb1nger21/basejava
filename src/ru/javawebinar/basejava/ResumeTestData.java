package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName){
        Resume newResume = new Resume(uuid,fullName);
        newResume.getContactMap().get(ContactType.PHONE).getContacts().add("79218550482");
        newResume.getContactMap().get(ContactType.SOCIAL).getContacts().addAll(List.of("grigory.kislin", "https://www.linkedin.com/in/gkislin", "https://github.com/gkislin"));
        newResume.getContactMap().get(ContactType.EMAIL).getContacts().add("gkislin@yandex.ru");

        newResume.getSectionsMap().get(SectionType.OBJECTIVE).setInformation("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        newResume.getSectionsMap().get(SectionType.PERSONAL).setInformation("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        newResume.getSectionsMap().get(SectionType.ACHIEVEMENT).setInformation(
                Arrays.asList(
                        "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                        "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                        "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                        "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                        "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                        "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
                ));

        newResume.getSectionsMap().get(SectionType.QUALIFICATIONS).setInformation(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        ));

        newResume.getSectionsMap().get(SectionType.EXPERIENCE).setInformation(
                Map.of(
                        new TimePeriod(YearMonth.of(2013, 10), YearMonth.now()),
                        new Knowledge("Java Online Projects", "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок."),
                        new TimePeriod(YearMonth.of(2014, 10), YearMonth.of(2016, 1)),
                        new Knowledge("Wrike", "Старший разработчик (backend)\nПроектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                        new TimePeriod(YearMonth.of(2012, 4), YearMonth.of(2014, 10)),
                        new Knowledge("RIT Center", "Java архитектор\nОрганизация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"),
                        new TimePeriod(YearMonth.of(2010, 12), YearMonth.of(2012, 4)),
                        new Knowledge("Luxoft (Deutsche Bank)", "Ведущий программист\nУчастие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                        new TimePeriod(YearMonth.of(2008, 6), YearMonth.of(2010, 12)),
                        new Knowledge("Yota", "Ведущий специалист\nДизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                        new TimePeriod(YearMonth.of(2007, 3), YearMonth.of(2008, 6)),
                        new Knowledge("Enkata", "Разработчик ПО\nРеализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."),
                        new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2007, 2)),
                        new Knowledge("Siemens AG", "Разработчик ПО\nРазработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                        new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(2005, 1)),
                        new Knowledge("Alcatel", "Инженер по аппаратному и программному тестированию\nТестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
                ));

        newResume.getSectionsMap().get(SectionType.EDUCATION).setInformation(Map.of(
                new TimePeriod(YearMonth.of(2013, 3), YearMonth.of(2013, 5)),
                new Knowledge("Coursera", "Functional Programming Principles in Scala\" by Martin Odersky"),
                new TimePeriod(YearMonth.of(2011, 3), YearMonth.of(2011, 4)),
                new Knowledge("Luxoft", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""),
                new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2005, 4)),
                new Knowledge("Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)"),
                new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(1998, 3)),
                new Knowledge("Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)"),
                new TimePeriod(YearMonth.of(1993, 9), YearMonth.of(1996, 7)),
                new Knowledge("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Аспирантура (программист С, С++)"),
                new TimePeriod(YearMonth.of(1987, 9), YearMonth.of(1993, 7)),
                new Knowledge("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Инженер (программист Fortran, C)"),
                new TimePeriod(YearMonth.of(1984, 9), YearMonth.of(1987, 6)),
                new Knowledge("Заочная физико-техническая школа при МФТИ", "Закончил с отличием")
        ));

        return newResume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.getContactMap().get(ContactType.PHONE).getContacts().add("79218550482");
        resume.getContactMap().get(ContactType.SOCIAL).getContacts().addAll(List.of("grigory.kislin", "https://www.linkedin.com/in/gkislin", "https://github.com/gkislin"));
        resume.getContactMap().get(ContactType.EMAIL).getContacts().add("gkislin@yandex.ru");

        resume.getSectionsMap().get(SectionType.OBJECTIVE).setInformation("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        resume.getSectionsMap().get(SectionType.PERSONAL).setInformation("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        resume.getSectionsMap().get(SectionType.ACHIEVEMENT).setInformation(
                Arrays.asList(
                        "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                        "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                        "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                        "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                        "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                        "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
                ));

        resume.getSectionsMap().get(SectionType.QUALIFICATIONS).setInformation(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        ));

        resume.getSectionsMap().get(SectionType.EXPERIENCE).setInformation(
                Map.of(
                        new TimePeriod(YearMonth.of(2013, 10), YearMonth.now()),
                        new Knowledge("Java Online Projects", "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок."),
                        new TimePeriod(YearMonth.of(2014, 10), YearMonth.of(2016, 1)),
                        new Knowledge("Wrike", "Старший разработчик (backend)\nПроектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                        new TimePeriod(YearMonth.of(2012, 4), YearMonth.of(2014, 10)),
                        new Knowledge("RIT Center", "Java архитектор\nОрганизация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"),
                        new TimePeriod(YearMonth.of(2010, 12), YearMonth.of(2012, 4)),
                        new Knowledge("Luxoft (Deutsche Bank)", "Ведущий программист\nУчастие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                        new TimePeriod(YearMonth.of(2008, 6), YearMonth.of(2010, 12)),
                        new Knowledge("Yota", "Ведущий специалист\nДизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                        new TimePeriod(YearMonth.of(2007, 3), YearMonth.of(2008, 6)),
                        new Knowledge("Enkata", "Разработчик ПО\nРеализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."),
                        new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2007, 2)),
                        new Knowledge("Siemens AG", "Разработчик ПО\nРазработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                        new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(2005, 1)),
                        new Knowledge("Alcatel", "Инженер по аппаратному и программному тестированию\nТестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
                ));

        resume.getSectionsMap().get(SectionType.EDUCATION).setInformation(Map.of(
                new TimePeriod(YearMonth.of(2013, 3), YearMonth.of(2013, 5)),
                new Knowledge("Coursera", "Functional Programming Principles in Scala\" by Martin Odersky"),
                new TimePeriod(YearMonth.of(2011, 3), YearMonth.of(2011, 4)),
                new Knowledge("Luxoft", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""),
                new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2005, 4)),
                new Knowledge("Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)"),
                new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(1998, 3)),
                new Knowledge("Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)"),
                new TimePeriod(YearMonth.of(1993, 9), YearMonth.of(1996, 7)),
                new Knowledge("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Аспирантура (программист С, С++)"),
                new TimePeriod(YearMonth.of(1987, 9), YearMonth.of(1993, 7)),
                new Knowledge("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "Инженер (программист Fortran, C)"),
                new TimePeriod(YearMonth.of(1984, 9), YearMonth.of(1987, 6)),
                new Knowledge("Заочная физико-техническая школа при МФТИ", "Закончил с отличием")
        ));
        System.out.println(resume);
    }
}