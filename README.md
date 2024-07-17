<h1 align="center"> /CopyPaste\ </h1>

## Возможности
- Чтение всех записей
- **Регистрация новых пользователей с подтверждением по электронной почте** (реализовано с помощью gmail)
- Создание новых записей (только после авторизации)
- Редактирование своих записей (ROLE_ADMIN может редактировать все записи)
- Удаление своих записей (ROLE_ADMIN может удалять все записи)
- Добавление комментариев к записям
- Дата записи, автор, ID и просмотры записываются в БД автоматически
  
## Установка
В проекте используется Java 17. Создайте базу данных PostgreSQL. 
Создайте переменные окружения 

| Переменная | Описание |
| --- | --- |
| DB_NAME | Название базы данных |
| DB_LOGIN | Логин от базы данных |
| DB_PASSWORD | Пароль от базы данных |
| MAIL_USERNAME | Gmail почта |
| MAIL_PASSWORD | Пароль от почты |

Если у вас стоит двухфакторная авторизация в Gmail, создайте 16-символьный пароль для приложения в настройках аккаунта.
(UPD: Теперь работает, только если у вас включена двухфакторная авторизация, а в переменной окружения вы указываете
отдельно созданный пароль для приложения).

Запустите jar файл командой:
```
java -jar CopyPaste.jar
```

## Методы
Все методы начинаются с localhost:8080/api/v1/

| URL                     | Метод | Назначение | Доступ |
|-------------------------| --- | --- | --- |
| author                  | GET | Список всех пользователей | ADMIN |
| author/new-author       | POST | Создание нового пользователя | Без авторизации |
| author/confirm/{token}  | GET | Подтверждение токена эл. почты | Без авторизации |
| paste                   | GET | Отображение всех записей | Без авторизации |
| paste/add               | POST | Добавить запись | AUTHOR, ADMIN |
| paste/{id}              | GET | Отобразить конкретную запись по ID | AUTHOR, ADMIN |
| paste/author/{username} | GET | Найти все записи автора | AUTHOR, ADMIN |
| paste/delete/{id}       | DELETE | Удалить запись | ADMIN (все), AUTHOR (только свои) |
| paste/update            | UPDATE | Изменить запись | ADMIN (все), AUTHOR (только свои) |
| comments/add/{pasteId}  | POST | Добавить комментарий к записи | AUTHOR, ADMIN |

Для удобства в файле проекта есть JSON-файл (.postman_collection.json), в котором записаны основные методы работы с Copypaste. 
Откройте его в Postman, чтобы не писать методы вручную.
