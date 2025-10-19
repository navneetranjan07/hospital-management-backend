# 🏥 Hospital Management Backend

This is the backend service for **CityCare Multispeciality Hospital Management System** — built using **Spring Boot**.  
It provides APIs for managing hospital operations such as patient registration, doctor details, appointments, and user authentication.

---

## 🚀 Features

- 👩‍⚕️ **Manage Doctors** – Add, update, view, and delete doctor profiles.  
- 🧑‍🤝‍🧑 **Manage Patients** – Register new patients, view records, and manage details.  
- 📅 **Appointments** – Book, update, cancel, and view appointments.  
- 🔐 **Authentication & Authorization** – Secure login and registration using JWT.  
- 🧾 **Database Integration** – JPA/Hibernate for ORM and persistence.  
- 🌍 **CORS Enabled** – For smooth frontend integration with React.  
- 🧰 **RESTful APIs** – Follows clean and structured API standards.

---

## 🛠️ Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | Java |
| Framework | Spring Boot |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL (or any relational DB) |
| Build Tool | Maven |
| Security | Spring Security + JWT |
| IDE | IntelliJ IDEA / VS Code / Eclipse |

---

```bash
## 📂 Project Structure
├── src/
│ ├── main/
│ │ ├── java/com/example/demo/
│ │ │ ├── controller/ # REST controllers
│ │ │ ├── entity/ # JPA entities
│ │ │ ├── repository/ # JPA repositories
│ │ │ ├── service/ # Business logic
│ │ │ └── HospitalManagementApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static / templates (if any)
│ └── test/ # Test cases
├── pom.xml # Maven dependencies
└── README.md # Project documentation
```
---

## ⚙️ Setup and Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/navneetranjan07/hospital-management-backend.git
cd hospital-management-backend
```

---

### 2️⃣ Configure Database (Update your application.properties)
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
```

---

### 3️⃣ Build the Project
```bash
mvn clean install
```

---

### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```

---

👨‍💻 Author

Navneet Ranjan
Backend Developer — CityCare Multispeciality Hospital Project

📧 Email: navnitranjan919904@gmail.com

🔗 GitHub: navneetranjan07
