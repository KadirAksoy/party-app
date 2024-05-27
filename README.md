## Resim, Dosya ve Not Yönetim Uygulaması
Bu projede kullanıcılar user,admin,super-admin olarak ayrılıyor. Adminler parti oluşturabilme gibi çeşitli yetkilere sahip. Userlar ise parti görüntüleme, katılma gibi benzer yetkilere sahiptir. Super-admin ise uygulama adminidir.

# Yapılandırmalar
Uygulama Başlatılmadan önce application.properties dosyasından database - mail gibi ayarlar yapılmalıdır.

### Önkoşullar

Projenin çalıştırılması için aşağıdaki yazılımların yüklü olması gerekmektedir:

- Docker
- Java

# Özellikler
Resim : Adminler partilere resim yükler.

Parti : Adminler parti oluşturup, silebilir. Süresi geçen partilerin aktifliği sistem tarafından otomatik olarak kapatılır. Partiye katılma ve çıkma işlemleri yapılır.

Request : Userlar admin olma isteği atabilirler.

Üyelik : Kullanıcılar ilk olarak user olarak üye olurlar. 

Mail : Kullanıcılara belli işlemlerde mail gönderilir.

# Swagger
http://localhost:8080/swagger-ui/index.html#/
