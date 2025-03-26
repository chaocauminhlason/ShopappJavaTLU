public class Main {
    public static void main(String[] args) {
        person person1 = new person(1L, "John", "123 Main St");
        System.out.println("Hello, World!");
    }
    public class person {
       
            private Long id;
            private String name;
            private String address;

            public person(Long id, String name, String address) {
                this.id = id;
                this.name = name;
                this.address = address;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }
        
}
}
