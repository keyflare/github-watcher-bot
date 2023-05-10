Folder must contain keys, secrets and other stuff like that
- 

 - local.config.json (copy example.config.json and fill in all the information)
 - \<your githubApp app private key file name>.der

To convert private key downloaded from GitHub (.pem) to the correct format (.der) execute the following command:

```openssl pkcs8 -topk8 -inform PEM -outform DER -in key.pem -out key.der -nocrypt```