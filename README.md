# LinkCutter
Микросервис для сокращения ссылок, повышающий комфортабельность использования ссылок,
написанный на Java.
Использование:
1)Отправить post запрос на `api/createShortLink` для укорачивания ссылки через
любой API клиент(Postman, Insomnia и тд)
{
"originalValue": "URL"
}

2)Отправить get запрос на `api/getOriginalLink?{shortValue}` для получение исходной ссылки
{
"originalValue": "URL",
"shortValue": "tokn2mx48s"
}
для последующего продолжения работы