abstract class Person {
     String firstName = "";
     String lastName = "";
     int age = 0;
     double height = 0;
     boolean isMale = false;
     Date updatedAt = new Date();

     String getFullName(){
         return this.firstName + " " + this.lastName;
     };

    abstract int getAge();

    abstract double getHeight();

    abstract boolean isMale();
}
