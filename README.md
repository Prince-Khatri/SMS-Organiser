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

## Screenshots
<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/992f8e9b-aa86-4ad9-a4f4-bb221b92adbe" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/e31cc956-d00f-47c3-9bfb-5377f53d020b" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/3fa590fa-8386-4daf-8067-dc614bb1f196" width="250"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/1ed8bda6-dd14-4b8b-8714-a9621ad7b85b" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/43e7f8d1-7f17-484d-84b7-33e2dbbf6128" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/c5127238-07e5-4203-85ef-c1f72db31419" width="250"/></td>
  </tr>
</table>

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


