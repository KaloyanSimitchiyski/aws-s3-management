# aws-s3-management

**Folder endpoints**

```HEAD /folders/{folderName}``` - check if the given folder exists
* ```404 NOT FOUND``` - folder does not exist
* ```200 OK``` - folder exists

```POST /folders/{folderName}``` - create a folder
* ```400 BAD REQUEST``` - folder already exists
* ```201 CREATED``` - folder successfully created

```DELETE /folders/folderName``` - delete an empty folder
* ```404 NOT FOUND``` - folder does not exist
* ```200 OK``` - folder successfully deleted

**File endpoints**

```GET /files/{folderName}/{fileName}``` - download a file from the specified folder
* ```404 NOT FOUND``` - file does not exist
* ```500 INTERNAL SERVER ERROR``` - unspecified error occured
* ```200 OK``` - file successfully downloaded

```GET /files/{fileName}``` - get all file names from the specified folder
* ```404 NOT FOUND``` - folder does not exist
* ```200 OK``` - successfully acquired file names list

```POST /files/{fileName}``` - upload a file in the specified folder via form data parameters
* ```400 BAD REQUEST``` - folder does not exist
* ```201 CREATED``` - file successfully uploaded

```DELETE /files/{folderName}/{fileName}```
* ```404 NOT FOUND``` - file does not exist
* ```200 OK``` - object successfully deleted
