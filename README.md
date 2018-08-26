# aws-s3-management

**Bucket endpoints**

```HEAD /buckets/{bucketName}``` - check if the given bucket exists
* ```404 NOT FOUND``` - bucket does not exist
* ```200 OK``` - bucket exists

````POST /buckets/{bucketName}``` - create a bucket
* ```400 BAD REQUEST``` - bucket already exists
* ```201 CREATED``` - bucket successfully created

```DELETE /buckets/bucketName``` - delete an empty bucket
* ```404 NOT FOUND``` - bucket does not exist
* ```200 OK``` - bucket successfully deleted

**Object endpoints**

```GET /objects/{bucketName}/{objectName}``` - download an object at the specified bucket
* ```400 BAD REQUEST``` - bucket does not exist
* ```404 NOT FOUND``` - object does not exist
* ```500 INTERNAL SERVER ERROR``` - unspecified error occured
* ```200 OK``` - object successfully downloaded

```GET /objects/{bucketName}``` - get all object names in the specified bucket
* ```404 NOT FOUND``` - bucket does not exist
* ```500 INTERNAL SERVER ERROR``` - unspecified error occured
* ```200 OK``` - successfully acquired object names list

```POST /objects/{bucketName}``` - upload an object in the specified bucket via form data parameters
* ```400 BAD REQUEST``` - bucket does not exist
* ```500 INTERNAL SERVER ERROR``` - unspecified error occured
* ```201 CREATED``` - object successfully uploaded

```DELETE /objects/{bucketName}/{objectName}```
* ```400 BAD REQUEST``` - bucket does not exist
* ```500 INTERNAL SERVER ERROR``` - unspecified problem occured
* ```200 OK``` - object successfully deleted
