# VaultX Entity Relationships - Spring Boot JPA Integration

## Overview
All entity relationships have been successfully mapped from the .NET DbContext to Spring Boot JPA annotations. The system uses a role-based access control hierarchy with proper entity associations.

---

## Entity Relationship Diagram

```
┌──────────────────────────────────────────────┐
│                    USER                      │
│  (Core entity - all users including admin)   │
└──────────────────────────────────────────────┘
                    ▲   ▲   ▲   ▲
           ┌────────┘   │   │   └────────┐
           │            │   │            │
      OneToOne      OneToOne OneToMany  OneToOne
        (1:1)         (1:1)   (1:many)   (1:1)
           │            │       │         │
           ▼            ▼       ▼         ▼
       EMPLOYEE       OTP   RESIDENCE  SOCIETY
                                 │        │
                             OneToMany   │
                               (1:m)     │
                                 │       │
                    ┌────────────┴───────┘
                    │
                    ▼
                 GUESTS ◄─────── OneToMany from User (invited by)
                    │
                    │ OneToOne
                    │
                    ▼
                 VEHICLE ──────► OneToMany to VehicleAccessLog
                    │
                    │
                    └──────► VEHICLE_ACCESS_LOG
```

---

## Detailed Relationships

### 1. **User Entity** (Central Hub)
```
User (userid: String) [PRIMARY]
├── Employee (1:1) - OneToOne
│   └── One user can be an employee (admin/staff)
│
├── Residences (1:many) - OneToMany
│   └── One user can own multiple residences
│
├── Guests (1:many) - OneToMany (invited by)
│   └── One user can invite multiple guests
│
├── Otp (1:1) - OneToOne
│   └── One user has one active OTP at a time
│
└── Society (1:1) - OneToOne (admin)
    └── One user is the society admin
```

**Mapping Configuration:**
```java
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private Employee employee;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Residence> residences = new ArrayList<>();

@OneToMany(mappedBy = "invitedBy", cascade = CascadeType.ALL)
private List<Guest> guests = new ArrayList<>();

@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private Otp otp;

@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private Society society;
```

---

### 2. **Employee Entity**
```
Employee (id: UUID) [PRIMARY]
└── User (many:1) - ManyToOne (Unique)
    └── Each employee is linked to exactly one user
    └── Delete: CASCADE
```

**Mapping Configuration:**
```java
@Column(name = "userid", length = 255)
private String userid;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
private User user;
```

---

### 3. **Residence Entity**
```
Residence (id: UUID) [PRIMARY]
├── User (many:1) - ManyToOne
│   └── Each residence belongs to one user (owner)
│   └── Delete: CASCADE
│
├── Guests (1:many) - OneToMany
│   └── One residence can host multiple guests
│   └── Cascade: ALL with OrphanRemoval
│
└── Vehicles (1:many) - OneToMany
    └── One residence can have multiple vehicles
    └── Cascade: ALL with OrphanRemoval
```

**Mapping Configuration:**
```java
@Column(name = "userid", length = 255)
private String userid;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
private User user;

@OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Guest> guests = new ArrayList<>();

@OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Vehicle> vehicles = new ArrayList<>();
```

---

### 4. **Guest Entity**
```
Guest (guestId: String) [PRIMARY]
├── User (many:1) - ManyToOne
│   └── Guest invited by this user
│   └── Delete: NO_ACTION (preserve guest record even if user deleted)
│
├── Residence (many:1) - ManyToOne
│   └── Guest visiting this residence
│
└── Vehicle (1:1) - OneToOne (Optional)
    └── Guest may arrive in a vehicle
```

**Mapping Configuration:**
```java
@Column(name = "userid", length = 255)
private String userid;

@Column(name = "residenceId")
private java.util.UUID residenceId;

@Column(name = "vehicleId", length = 255)
private String vehicleId;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
private User invitedBy;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "residenceId", referencedColumnName = "id", insertable = false, updatable = false)
private Residence residence;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId", insertable = false, updatable = false)
private Vehicle vehicle;
```

---

### 5. **Vehicle Entity**
```
Vehicle (vehicleId: String) [PRIMARY]
├── Residence (many:1) - ManyToOne
│   └── Vehicle belongs to this residence
│   └── Delete: SET_NULL (vehicle record preserved if residence deleted)
│
├── Guest (1:1) - OneToOne (Optional)
│   └── Vehicle may have a guest traveling in it
│
└── VehicleAccessLog (1:many) - OneToMany
    └── Multiple access logs for this vehicle
```

**Mapping Configuration:**
```java
@Column(name = "residentid")
private java.util.UUID residentId;

@ManyToOne(fetch = FetchType.LAZY)
@OnDelete(action = OnDeleteAction.SET_NULL)
@JoinColumn(name = "residentid", referencedColumnName = "id", insertable = false, updatable = false)
private Residence resident;

@OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
private Guest guest;
```

---

### 6. **Society Entity**
```
Society (societyId: String) [PRIMARY]
└── User (1:1) - OneToOne
    └── Society admin (manages the gated community)
    └── Delete: NO_ACTION
```

**Mapping Configuration:**
```java
@Column(name = "user_id", length = 255)
private String userId;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", referencedColumnName = "userid", insertable = false, updatable = false)
private User user;
```

---

### 7. **OTP Entity**
```
OTP (id: UUID) [PRIMARY]
└── User (many:1) - ManyToOne
    └── OTP belongs to this user
    └── Delete: CASCADE (delete OTP if user deleted)
```

**Mapping Configuration:**
```java
@Column(name = "userUserid", length = 255)
private String userUserid;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userUserid", referencedColumnName = "userid", insertable = false, updatable = false)
private User user;
```

---

### 8. **VehicleAccessLog Entity**
```
VehicleAccessLog (id: UUID) [PRIMARY]
└── Vehicle (many:1) - ManyToOne
    └── Log entry for this vehicle
    └── Delete: CASCADE (delete log if vehicle deleted)
```

**Mapping Configuration:**
```java
@Column(name = "vehicleId", length = 255)
private String vehicleId;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId", insertable = false, updatable = false)
private Vehicle vehicle;
```

---

## Key JPA Concepts Used

### 1. **Cascade Types**
- `CascadeType.ALL` - Propagate all operations to child entities
- `CascadeType.PERSIST` - Cascade only save operations
- `orphanRemoval = true` - Delete child if parent is deleted

### 2. **FetchType**
- `FetchType.LAZY` - Load related data only when accessed (Recommended for performance)
- `FetchType.EAGER` - Load related data immediately (Use sparingly)

### 3. **Delete Behaviors**
- `CASCADE` - Delete child when parent is deleted
- `SET_NULL` - Set FK to null when parent is deleted
- `NO_ACTION` - Keep child even if parent is deleted

### 4. **Join Configuration**
- `insertable = false, updatable = false` - Foreign key is read-only (managed by relationship)
- `referencedColumnName` - Specifies the column being referenced in parent table

---

## Testing Relationships

### Example Query with Relationships
```java
// Get a user with all related data
User user = userRepository.findById(userid).orElseThrow();

// Access relationships
user.getEmployee();              // Get employee info
user.getResidences();            // Get all residences owned
user.getGuests();                // Get all invited guests
user.getOtp();                   // Get OTP
user.getSociety();               // Get society info

// Navigate deeper
Residence residence = user.getResidences().get(0);
residence.getGuests();           // Get all guests in this residence
residence.getVehicles();         // Get all vehicles

// Access vehicle details
Vehicle vehicle = residence.getVehicles().get(0);
vehicle.getGuest();              // Get guest (if any)
vehicle.getAccessLogs();         // Get access history
```

---

## Build Status
✅ **Successfully compiled** with all JPA relationships integrated
- Total Entities: 8
- Total Relationships: 16
- No compilation errors

---

## Next Steps
1. Create JPA Repositories for each entity
2. Implement authentication & authorization layer
3. Create service layer with business logic
4. Build REST controllers with proper endpoint design
5. Add database migration scripts (if needed)


