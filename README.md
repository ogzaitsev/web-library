# web-library
simple web application for library
## Инструкция для запуска приложения
* Создайте БД в PostgreSQL
* В `src/main/resources/application.properties` укажите:
   * `spring.datasource.url= ` ссылку на БД
   *  `spring.datasource.username=` Имя пользователя БД
   *  `spring.datasource.password=` Пароль
   *  `#spring.sql.init.data-locations=classpath:sql/data.sql` - Если хотите добавить тестовые данные, раскомментируйте строку (убрать #)
   *  `server.port=8080` - порт сервера
## Работа с API
 * АПИ интерфейс - по адресу `http://localhost:8080/api`
 * При запросе возвращается массив `content` и массив `metadata`. Данные возвращаются постранично, списком внутри content. Для изменения размера страницы используется параметр `pageSize`. Дефолтное значение pageSize=10

    
    http://localhost:8080/api?pageSize=3

  Возвращает максимум 3 элемента
   
* Внутри `metadata` возвращается nextId. При запросе следующей страницы его необходимо передать в параметре `lastId`

    http://localhost:8080/api?&lastId=5

  Возвращает элементы начиная с id = 5 (не включая 5)

    http://localhost:8080/api?pageSize=3&lastId=5

  Возвращает элементы начиная с id = 5, размер страницы = 3 
  
