
public class Program {

    private static void demoFunctionalInterfaces() {
        // instance of the functional interface MyInterface.
        MyInterface func = (p1, p2) -> { return p1 + p2; };
        
        System.out.println(func.myMethod("My String ", 4.2));
    }
    
    private static void demoAnonymousClasses() {
        // Two variables, f & g as instances of anonymous classes,
        // implementing MyOtherInterface.
        MyOtherInterface f = new MyOtherInterface() {
            public String myMethod1(String myParam1, double myParam2) {
                return "f.myMethod1: " + myParam1 + "," + myParam2;
            }
            public void myMethod2(int myParam3) {
                System.out.println("f.myMethod2: " + myParam3);
            }
        };
        
        MyOtherInterface g = new MyOtherInterface() {
            public String myMethod1(String myParam1, double myParam2) {
                return "g.myMethod1: " + myParam2 + "," + myParam1;
            }
            public void myMethod2(int myParam3) {
                System.out.println("g.myMethod2: " + myParam3);
            }
            
        };
        
        System.out.println(f.myMethod1("abc", 4.2));
        f.myMethod2(32);
        
        System.out.println(g.myMethod1("def", 5.4));
        g.myMethod2(55);
    }
    
    public static void demoSwitch() {
        String value = "abc";
        
        // demonstrate a switch value forking behavior based
        // on how a value is matching one value from a given set.
        switch(value) {
        case "def":
            System.out.println("case 1: def");
            break;
        case "xyz":
            System.out.println("case 2: xyz");
            break;
        case "abc":
            System.out.println("case 3: abc");
            break;
        default:
            System.out.println("Unexpected: " + value);
        }
    }
    
    public static void demoTernary() {
        int number = 0;
        
        // simple usage of a ternary expression
        // prints "large number" if number is greater than 1000 and 
        // "not so large" otherwise.
        String result1 = (number > 1000) ? "large number" : "not so large";
        System.out.println(result1);
        
        // nested usage of ternary expressions:
        // prints "negative", "zero" or "positive" depending on the value of number.
        String result2 = (number < 0) 
                ? "negative"
                : (number == 0) ? "zero" : "positive";
        System.out.println(result2);
    }
    
    public static void main(String[] args) {
        demoFunctionalInterfaces();
        demoAnonymousClasses();
        demoSwitch();
        demoTernary();
    }
}
