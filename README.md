# CS360_FinalProject

## Warehouse Inventory Management App
### Overview
This mobile application was developed to help warehouse staff efficiently manage inventory levels and send low inventory SMS alerts. The main goal of the app is to provide a simple, user-friendly interface to add, view, and delete inventory items while also allowing users to configure SMS alerts if stock falls below a critical threshold. It was built with first-time full-stack mobile app developers in mind and designed to reflect practical, real-world warehouse operations.

### User Needs and UI Design
The app was created to address two primary user needs: tracking inventory items with their respective quantities and sending SMS alerts for low inventory. To support these, several screens and features were developed, including a login screen, an inventory dashboard, and an SMS notification screen. The inventory dashboard was designed with a scrollable interface showing all inventory items and clear input fields for adding new ones. Button placements and form validations ensured that user interaction was intuitive and efficient. The SMS notification screen included permission handling, phone number input, and messaging controls, all structured with accessibility and feedback messages to improve user trust and experience.

### Development Approach
As someone who typically works on backend development using Python and R, approaching full-stack mobile development was a new experience. I took a modular approach by separating the logic into multiple classes, such as database helpers, RecyclerView adapters, and individual activity files. While I created more classes than necessary at times, this helped me better organize responsibilities and see the importance of class structure in Android development. These techniques can be used in future projects to maintain scalability and improve code maintenance.

### Testing and Validation
Testing was performed continuously using the Android Emulator to simulate real-world usage. I validated login functions, tested database CRUD operations, and checked SMS permissions by allowing and denying them during runtime. This process revealed some layout issues and helped refine permission-based behavior—ensuring that the app functioned properly even when SMS permissions were denied. Testing is critical to ensure a reliable user experience and maintain application stability.

### Challenges and Successes
One challenge I had to overcome was managing runtime SMS permissions and ensuring the app still worked when users denied access. I had to research Android's permission model and write logic that would degrade gracefully. This required innovation and careful testing. The component I was most proud of was the integration of the SMS notification feature, which required me to go beyond basic UI and storage and explore Android’s native system services like SmsManager. It was rewarding to see the app successfully send real messages from the emulator, which tied together permissions, input validation, and system services.
