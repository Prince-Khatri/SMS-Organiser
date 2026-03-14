# SMS Organizer

SMS Organizer is an Android application that helps organize SMS messages into useful categories like Personal, Transactions, Promotions, Spam, etc.

## Integrated Intelligence
This application uses the classification logic developed in the **[SMS Organiser Model](https://github.com/Prince-Khatri/SMS-Organiser-Model)** repository to accurately identify and sort incoming SMS bodies.

## Features
- **Automatic Categorization:** Groups messages into OTP, Transactional, Personal, and Spam.
- **Clean UI:** Utilizes a modern `RecyclerView` and category chips for easy navigation.
- **Local Storage:** Built with the `Room` persistence library (SMSDao, SMSRepository) for fast, offline access.
- **Permission Handling:** Robust runtime permission requests for SMS reading.
- **Bottom Navigation:** Quick access to different inbox views.

## Tech Stack
- **Language:** Java
- **UI:** Android XML, Material Design
- **Database:** Room Persistence Library

## Getting Started
1. Clone the repository.
```
git clone https://github.com/Prince-Khatri/SMS-Organiser.git
```
2. Open in Android Studio.
```
cd SMS-Organiser
``` 
3. Sync Gradle and run on an emulator or physical device.
```
gradlew clean assembleDebug
./gradlew clean assembleDebug
```
4. Grant SMS permissions to allow the app to organize your inbox.

---
*Developed as part of the SMS Organiser ecosystem.*


