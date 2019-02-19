# ROOMDB KOTLIN WITH MVVM

**What is Room?**<br/>
Room is part of the Android Architecture Components presented in the Google I/O 2016. It is not an ORM, it is a whole library that allows us to create and manipulate SQLite databases easier, by using annotations we can define our databases, its tables and operations; Room will automatically translate these annotations into SQLite instructions/queries to perform the correspondent operations into the DB engine.<br/>
 **Three major components of Room**<br/>
 * **Database:** It represents the DB, it is an object that holds a connection to the SQLite DB  and all the operations are executed through it. It is annotated with `@Database`.<br/>
 * **Entity:** Represents a table within the Room Database. It should be annotated with @Entity.<br/>
 * **DAO:** An interface that contains the methods to access the Database. It is annotated with @Dao.<br/>
 
# Example:<br/>
**Entities**<br/>
````
@Entity(tableName = "studentEntry")
data class Student(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        @ColumnInfo(name = "machine_name")
````

* All the classes that represent an entity of the database have to be annotated with `@Entity`.
* With the annotation` @PrimaryKey(autoGenerate = true)` we are indicating that id is the primary key of the entity and should be autoGenerate by the database engine.
* By default, Room uses the field names as the column names in the database. If you want a column to have a different name, add the `@ColumnInfo` annotation to a field.
* When a class is annotated with `@Entity` the name of the tablet will be the name of the class, if we want to use a different one we have to add the tableName property along with the @Entity annotation.
`@TypeConverters`
@TypeConverters annotation has to be used when we declare a property which type is a custom class, a list, date type or any other type that Room<br/>

**DAOs**<br/>
Data Access Objects or DAOs are used to access our data when we implement Room. Each DAO have to include a set of methods to manipulate the data (insert, update, delete or get).<br/>
````
@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("SELECT * FROM Gender WHERE name == :name")
    fun getStudentByName(name: String): List<Gender>

    @Query("SELECT * FROM Gender")
    fun getStudent(): List<Student>
}
````
* All the DAOs have to be annotated with @Dao.
* A function annotated with @Insert , @Update or @Deletehave to receive an instance of the desired class as a parameter, which represents the object that we want to insert, update or delete respectively.
* In the case of insert or update operations, we can use the property onConflict to indicate what to do when a conflict performing the operation happens. The strategies available to use are: REPLACE , ABORT , FAIL , IGNORE and ROLLBACK.
* `Query ` It perform the select operation to get the specific operation from one or more entities.

**DataBase**<br/>
It represents the DB, it holds a connection to the actual SQLite DB.<br/>
````
@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {  
    abstract fun studentDao(): StudentDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
````
* This is an abstract class that has to extend from `RoomDatabase`
* It has to be annotated with `@Database`, it receives a list of entities with all the classes that compose the database (all these classes have to be annotated with `@Entity`). We also have to provide a `database version`.
* We have to declare an abstract function for each of the entities included in the @Database annotation, this function has to return the correspondentDAO (A class annotated with @Dao).
* Finally, we declare a companion object to get static access to the method getAppDataBase which gives us a singleton instance of the database.



