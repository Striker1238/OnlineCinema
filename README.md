# 🎬 OnlineCinema — JavaFX Клиент

Это клиентская часть онлайн-кинотеатра, написанная на Java с использованием JavaFX. 
Для пользователя есть множество функций, такие как: регистрация аккаунта, просмотр медиа, добавления медиа в закладки, просмотр статисти профиля и редактирования его, комментирование, создание коллекций и иногое другое.

---

## 🛠️ Технологии

- **Java 21**
- **JavaFX 21**
- **Maven** (для сборки и зависимостей)
- **FXML** (интерфейс)

---

## 📦 Установка и запуск

### 1. Установи зависимости

Убедись, что установлены:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [JavaFX SDK 21](https://www.oracle.com/java/technologies/install-javafx-sdk.html)

### 2. Настрой JavaFX (если запускаете jar)
Добавь в аргументы VM:

```bash
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
