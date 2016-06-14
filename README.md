# jhipster

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Development

Poner en funcionamiento el proyecto:
Este apartado lo añadiremos a nuestro proyecto en un fichero Readme para que no sea necesario mirar esta documentación puesto que generalmente ese fichero es el que te indica cómo poner en marcha el proyecto.
Antes que nada deberemos instalar JHipster y para ello necesitamos instalar varias tecnologías que permitan el correcto funcionamiento de JHipster en su propia web explican paso a paso las instalaciones necesarias, en nuestra bibliografía podréis encontrar el link.
Si a alguien le interesase poner en funcionamiento el proyecto en casa nosotros facilitamos los pasos que debería de seguir desde el entorno de desarrollo IntelliJ IDEA.
 File->New->Project from Version Control -> GitHub
 En la URL del Repositorio Git pegamos: https://github.com/marcio81/BirringPosgres.git
 Una vez nos cargue todos los archivos tendremos que configurar la base de datos para vincularla al proyecto, en nuestro caso utilizamos PostgreSQL y creemos que sería de utilidad mencionar que no se puede acceder a la base de datos con el usuario por defecto que te proporciona PostgreSQL, sino que se tendrá que crear uno especialmente para nuestra base de datos, el nombre de la base de datos podremos encontrarlo en la carpeta del proyecto/src/main/resources/config en el archivo application-dev.yml dentro del datasource hay una línea de código que empieza por "url:", la última palabra de esa línea será el nombre de la base de datos.
9
 Será necesario otra key para la utilización de la API de Google Maps, para incluirla deberá de cambiar el script en el índex.
 Después de la configuración de la base de datos en caso de que el usuario quiera poner en marcha el registro con validación por email deberá modificar el archivo application-dev.yml, en concreto las líneas "username" y "password" dentro de "mail".
 Después ya se podrá ejecutar el proyecto y si todo funciona correctamente nos mostrará un link para acceder a la aplicación.



Before you can build this project, you must install and configure the following dependencies on your machine:
1.Java 
  sudo echo oracle-java-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
  sudo apt-get install -y --force-yes oracle-java8-installer
  sudo update-java-alternatives -s java-8-oracle
  
 2. GIT: We use GIT to download the source code.
 
   sudo apt-get install git
 
3. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    npm install -g gulp

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    gulp

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.


## Building for production

To optimize the jhipster client for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript/` and can be run with:

    gulp test



## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `jhipster`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/jhipster.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Maven / Tasks: `-Pprod clean package`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
