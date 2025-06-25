Site.Login(username, password, null);
Auth.VerificationResult result =  UserManagement.verifyVerificationMethod('', password , Auth.VerificationMethod.PASSWORD);

Auth.VerificationResult result =  UserManagement.verifyVerificationMethod('', password , Auth.VerificationMethod.TOTP);