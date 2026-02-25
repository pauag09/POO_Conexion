public class App {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");

        Customer c1 = new Customer(1, "Paulina", "Arenas", 21, "pau.doe@example.com");
        Customer c2 = new Customer(2, "Alejandro", "cacho", 21, "ale.doe@example.com");
        
        MySqlCRUD conn = new MySqlCRUD();
    }
}
