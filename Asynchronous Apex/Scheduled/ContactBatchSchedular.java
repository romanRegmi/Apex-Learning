/*
* Schedule the ContactBatch class
*/

global class ContactBatchSchedular implements System.Schedulable{
    global void execute(System.SchedulableContext SC){
        ContactBatch batch = new ContactBatch();
        final Integer BATCH_SIZE = 10;
        Database.executeBatch(batch, BATCH_SIZE);
    }
}

/*
* ContactBatchSchedular schedular = new ContactBatchSchedular();
* schedular.execute(null);
* Using CRON
* final String CRON_EXP = '0 0 12 * * ?';
* System.schedule('ContactBatchSchedular', CRON_EXP, schedular);
*/ 