# Book Info Manager – Android App

A multi-activity Android application built in Java that handles book data entry, inter-activity communication, camera usage, and common Android implicit intents.

## Features

### Activity 1 – Book Input
- Enter title, author, and publication date (MM/YYYY)  
- Custom DatePickerDialog  
- Pass data using a Parcelable object via explicit intent  

### Activity 2 – Book Details
- Display received book data  
- Capture photo using ACTION_IMAGE_CAPTURE  
- Runtime camera permission handling  
- Navigate to next activity via explicit intent  

### Activity 3 – Email & Web Intents
- Generate and send an email using ACTION_SENDTO  
- Open a user-entered web URL using ACTION_VIEW

## State Management
- onSaveInstanceState() implemented in all activities
- Restores input fields, email content, camera image, and search text on rotation

