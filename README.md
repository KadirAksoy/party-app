## Parti Eğlence Uygulaması

Kullanılan teknolojiler.

- Spring Boot
- MySQL
- Swagger
- Spring Security
- JWT (JSON Web Token)
- Kafka
- Docker
- Redis
- JavaMailSender

Bu projede kullanıcılar user,admin,super-admin olarak ayrılıyor. Adminler parti oluşturabilme gibi çeşitli yetkilere sahip. Userlar ise parti görüntüleme, katılma gibi benzer yetkilere sahiptir. Super-admin ise uygulama adminidir.

# Yapılandırmalar
Uygulama Başlatılmadan önce application.properties dosyasından mail ayarları yapılmalıdır.

### Önkoşullar

Projenin çalıştırılması için aşağıdaki yazılımların yüklü olması gerekmektedir:

- Docker
- Maven
- Java

### Kurulum

1. Bu projeyi klonlayın:

```
https://github.com/KadirAksoy/restaurant-app.git
```



2. Gerekli yapıları docker-compose.yml dosyası ile başlatın:

```
docker-compose up -d
```

3. Projeyi derlemek ve çalıştırmak için aşağıdaki Maven komutlarını kullanın:

```
docker build -t party_app:0.0.1 .   ---> image oluşturur.
docker run -d --name party_app -p 8080:8080 party_app:0.0.1
```

# Özellikler
Resim : Adminler partilere resim yükler.

Parti : Adminler parti oluşturup, silebilir. Süresi geçen partilerin aktifliği sistem tarafından otomatik olarak kapatılır. Partiye katılma ve çıkma işlemleri yapılır.

Request : Userlar admin olma isteği atabilirler.

Üyelik : Kullanıcılar ilk olarak user olarak üye olurlar. 

Mail : Kullanıcılara belli işlemlerde mail gönderilir.

# Swagger
http://localhost:8080/swagger-ui/index.html#/
