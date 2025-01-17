# kafka-hw7

1. Запустить docker-compose
В докере запустятся kafka, postgres и 3 сервиса

documents-api - сервис генерирующий заявки на закупку

методы можно посмотреть в swagger: http://localhost:8080/api/swagger-ui/index.html
Для тестирования можно использовать api метод order/generate - генерирует 10 случайных заявок

validate-api - сервис принимающий заявки, сохраняющий их в базу. Можно одобрить/отклонить заявку (статус ACCC/RJCT)
методы можно посмотреть в swagger: http://localhost:8081/api/swagger-ui/index.html

Для тестирования есть api метод, случайным образом одобряющий или отклоняющий все ожидающие заявки: /api/v1/order/randomValidateOrNo

analysis-service - сервис, анализирующий кол-во и общую сумму принятых заявок за определённое время (в рамках тестового проекта выбрано временное окно в 1 минуту)

2. В свагере http://localhost:8080/api/swagger-ui/index.html Вызвать метод /api/v1/order/generate  - в логе приложения documents-api будут выведены записи в kafka
3. В свагере http://localhost:8081/api/swagger-ui/index.html Вызвать метод /api/v1/order/randomValidateOrNo - заявки будут одобрены или отклонены. 
4. В логе приложения documents-api можно увидеть результат - одобрена или отклонена заявка
5. В логе приложения analysis-service - количество и общая сумма одобренных заявок в течении временного окна в одну минуту.




