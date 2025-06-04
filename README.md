# 🎬 OnlineCinema — JavaFX Клиент

Это клиентская часть онлайн-кинотеатра, написанная на Java с использованием JavaFX. Пользователь может регистрироваться, просматривать медиа, изменять статус просмотра, просматривать профиль, комментарии, коллекции и др.

---

## 🛠️ Технологии

- **Java 21**
- **JavaFX 21**
- **Maven** (для сборки и зависимостей)
- **FXML** (интерфейс)
- **SceneBuilder** (разметка UI)
- **HTTP Client API** (встроенный в Java)
- **Jackson** (для работы с JSON)

---

## 📦 Установка и запуск

### 1. Установи зависимости

Убедись, что установлены:

- [JDK 21](https://adoptium.net/)
- [JavaFX SDK 21](https://openjfx.io/)
- [Apache Maven](https://maven.apache.org/)

### 2. Настрой JavaFX (если запускаешь jar)
Добавь в аргументы VM:

```bash
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
