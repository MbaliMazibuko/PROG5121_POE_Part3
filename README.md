## Part 3 - Store data and display report

Arrays used (filled by the program, not typed into the code):
- Sent Messages
- Disregarded Messages
- Stored Messages
- Message Hash
- Message ID

Main menu extra option:
4) Stored Messages

Stored Messages menu:
a) Display sender and recipient of stored messages
b) Display the longest message
c) Search by message ID
d) Search by recipient
e) Delete a message using the message hash
f) Display a full report
g) Back

The report shows Message Hash, Recipient and Message.
Stored messages can also be loaded from stored_messages.json.

Part 3 test data:
1. +27834557896 | Did you get the cake? | Sent
2. +27838884567 | Where are you? You are late! I have asked you to be on time. | Stored
3. +27834484567 | Yohoooo, I am at your gate. | Disregard
4. 0838884567 | It is dinner time! | Sent
5. +27838884567 | Ok, I am leaving without you. | Stored

Classes:
- MessageStore
- MessageStoreTest

Git branch: Part3Reports

---

## Files
- Login.java
- QuickChatApp.java
- Message.java
- MessageStore.java
- LoginTest.java
- MessageTest.java
- MessageStoreTest.java
- stored_messages.json
