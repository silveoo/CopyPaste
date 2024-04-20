<h1 align="center"> /CopyPaste\ </h1>

## Возможности
- Чтение всех записей
- Регистрация новых пользователей
- Создание новых записей (только после авторизации)
- Редактирование своих записей (ROLE_ADMIN может редактировать все записи)
- Удаление своих записей (ROLE_ADMIN может удалять все записи)
- Дата записи, автор и ID записываются в БД автоматически
## Установка
В проекте используется Java 17. Создайте базу данных PostgreSQL и в файле application.yaml замените url, username, password на данные своей базы.
Запустите jar файл командой:
```
java -jar CopyPaste.jar
```
Стандартные значения: 
```
url: jdbc:postgresql://localhost:5432/copypaste
username: postgres
password: root
```
## Методы
Все методы начинаются с localhost:8080/api/v1/

| URL | Метод | Назначение | Доступ |
| --- | --- | --- | --- |
| author | GET | Список всех пользователей | ADMIN |
| author/new-author| POST | Создание нового пользователя | Без авторизации |
| author/{username}| GET | Найти все записи автора | AUTHOR, ADMIN |
| paste | GET | Отображение всех записей | Без авторизации |
| paste/add | POST | Добавить запись | AUTHOR, ADMIN |
| paste/{id} | GET | Отобразить конкретную запись по ID | AUTHOR, ADMIN |
| paste/delete/{id} | DELETE | Удалить запись | ADMIN (все), AUTHOR (только свои) |
| paste/update | UPDATE | Изменить запись | ADMIN (все), AUTHOR (только свои) |
