public class A implements Queueable {
    public void execute(Queueable context) {
        // Test to make sure that unit test are not running before chaining the call. We don't want to chaing another job during a unit test run.
        if(!Test.isRunningTest()){
            System.enqueueJob(new B());
        }
    }
}