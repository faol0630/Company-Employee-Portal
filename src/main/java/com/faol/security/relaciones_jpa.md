
# RELACIONES JPA

## @OneToOne
## @OneToOne
## @OneToOne

### en el entity que se quiere colocar la clave foranea insertamos el one to one.
### en esta app queremos agregar address a employee por lo tanto la clave foranea
### de address la insertamos en employee

### //se podria dejar solo @OneToOne pero podria ocasionar problemas
### //cascade escoge el unico caso en que va a haber comportamiento en cascada:
### //cascade persist no guarda address si guardo employee o sea 2 inserts/si elimino employee, address no se elimina
### //cascade remove elimina address si elimino employee o sea 2 deletes
### //cascade merge actualiza address si actualizo employee o sea 2 updates
### //cascade refresh no guarda address cuando se guarda employee
### //merge no crea address cuando se crea employee
### //detach no crea address cuando crea employee
### //cascadetype por defecto es none.
### //con persist, primero se crea employee y despues se hace un update agregando address_id solamente.
### //join column sirve para personalizar el nombre de la clave foranea dentro de la tabla employee

### @OneToOne(targetEntity = Address.class, cascade = CascadeType.PERSIST )
### @JoinColumn(name = "address_int_id") //PERSONALIZAR NOMBRE CLAVE FORANEA
### private Address address;

## @OneToMany
## @OneToMany
## @OneToMany

### fetch = FetchType.LAZY: no trae la lista cuando se pide el entity que hace las
### veces de One. Ejemplo en una relacion Department - Employee , donde Department
### es One y employee es Many, cuando se pide un Deparment, este no viene con la 
### lista de employees.

### fetch = FetchType.EAGER: lo contrario a LAZY, cuando se pide un Department, 
### viene con todos los employees.

### mapped by se coloca en el @OneToMany y se usa para que el id del One pase como clave
### foranea en el Many sin crear una tabla intermedia.

### en mapped by se coloca en nombre del atributo del @ManyToOne en la otra tabla
### en este caso en @ManyToOne el atributo se llama department.

### para evitar bucles infinitos en una relacion bidireccional usamos las anotaciones
### @JsonManagedReference y @JsonBackReference 

### en el entity dueño de la relacion, osea el que tiene el @OneToMany con el mapped, 
### colocamos @JsonManagedReference.

### en el otro entity que tiene @ManyToOne colocamos @JsonBackReference

### en el entity que hace las veces de One queda asi:

### @JsonManagedReference
### @OneToMany(targetEntity = Employee.class, fetch = FetchType.LAZY, mappedBy = "department")
### private List<Employee> employeeList;

### en el entity que hace las veces de Many queda asi:

### @JsonBackReference
### @ManyToOne(targetEntity = Department.class)
### private Department department;

## @ManyToOne
## @ManyToOne
## @ManyToOne

### es lo mismo que @OneToMany invirtiendo el orden. El one y el many quedan igual que @OneToMany


## @ManyToMany
## @ManyToMany
## @ManyToMany

### quedarian asi :

### en la tabla Client:
### @ManyToMany(targetEntity = Company.class)
### List<Company> companyList 

### en la tabla Company
### @ManyToMany(targetEntity = Client.class, fetch = FetchType.LAZY)
### List<Client> clientList

### [ CREAR ENTITY CLIENT CON TODA LA LOGICA(REPOSITORY, SERVICE, CONTROLLER, ETC) ]
### [ CREAR ENTITY CLIENT CON TODA LA LOGICA(REPOSITORY, SERVICE, CONTROLLER, ETC) ]
### [ CREAR ENTITY CLIENT CON TODA LA LOGICA(REPOSITORY, SERVICE, CONTROLLER, ETC) ]
### [ CREAR ENTITY CLIENT CON TODA LA LOGICA(REPOSITORY, SERVICE, CONTROLLER, ETC) ]
### [ CREAR ENTITY CLIENT CON TODA LA LOGICA(REPOSITORY, SERVICE, CONTROLLER, ETC) ]

# parkingSpot - employee , relacion @OneToOne
