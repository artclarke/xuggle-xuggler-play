This quickstart will get you going with [Play! Framework](http://playframework.org/) on the [Cedar](http://devcenter.heroku.com/articles/cedar) stack.

Sample code is available on [github](https://github.com/heroku/devcenter-play) along with this document. Edits and enhancements are welcome. Just fork the repository, make your changes and send us a pull request.

## Prerequisites

* Basic Java knowledge, including an installed version of the JVM.
* An installation of [Play Framework](http://www.playframework.org/download) version 1.2.3 or later (make sure you have the `play` command on your path).
* Your application must run on the [OpenJDK](http://openjdk.java.net/) version 6.
* A Heroku user account.  [Signup is free and instant.](https://api.heroku.com/signup)

## Local Workstation Setup

We'll start by setting up your local workstation with the Heroku command-line client and the Git revision control system; and then logging into Heroku to upload your `ssh` public key.  If you've used Heroku before and already have a working local setup, skip to the next section.

<table>
  <tr>
    <th>If you have...</th>
    <th>Install with...</th>
  </tr>
  <tr>
    <td>Mac OS X</td>
    <td style="text-align: left"><a href="http://toolbelt.herokuapp.com/osx/download">Download OS X package</a></td>
  </tr>
  <tr>
    <td>Windows</td>
    <td style="text-align: left"><a href="http://toolbelt.herokuapp.com/windows/download">Download Windows .exe installer</a></td>
  </tr>
  <tr>
    <td>Ubuntu Linux</td>
    <td style="text-align: left"><a href="http://toolbelt.herokuapp.com/linux/readme"><code>apt-get</code> repository</a></td>
  </tr>
  <tr>
    <td>Other</td>
    <td style="text-align: left"><a href="http://assets.heroku.com/heroku-client/heroku-client.tgz">Tarball</a> (add contents to your <code>$PATH</code>)</td>
  </tr>
</table>

Once installed, you'll have access to the `heroku` command from your command shell.  Log in using the email address and password you used when creating your Heroku account:

    :::term
    $ heroku login
    Enter your Heroku credentials.
    Email: adam@example.com
    Password: 
    Could not find an existing public key.
    Would you like to generate one? [Yn] 
    Generating new SSH public key.
    Uploading ssh public key /Users/adam/.ssh/id_rsa.pub

Press enter at the prompt to upload your existing `ssh` key or create a new one, used for pushing code later on.

## Write your app
    
You can run any Play! application on Heroku. If you don't already have one, you can create a basic Play! application with

    $ play new helloworld
    ~        _            _ 
    ~  _ __ | | __ _ _  _| |
    ~ | '_ \| |/ _' | || |_|
    ~ |  __/|_|\____|\__ (_)
    ~ |_|            |__/   
    ~
    ~ play! 1.2.3, http://www.playframework.org
    ~
    ~ The new application will be created in /Users/jjoergensen/dev/tmp/helloworld
    ~ What is the application name? [helloworld] 
    ~
    ~ OK, the application is created.
    ~ Start it with : play run helloworld
    ~ Have fun!
    ~

This creates a project called helloworld with a simple controller class `Application.java`:

### app/controllers/Application.java

    package controllers;

    import play.*;
    import play.mvc.*;

    import java.util.*;

    import models.*;

    public class Application extends Controller {

        public static void index() {
            render();
        }

    }
    
## Declare Dependencies

Play dependencies are declared in `conf/dependencies.yml`. Here's an example `dependencies.yml` for the project we just created:

### dependencies.yml

    # Application dependencies

    require:
        - play

Prevent build artifacts from going into revision control by creating this file:

### .gitignore

    :::term
    bin/
    data/
    db/
    dist/
    logs/
    test-result/
    lib/
    tmp/
    modules/
    
## Declare Process Types With Foreman/Procfile

To run your web process, you need to declare what command to use.  We'll use `Procfile` to declare how our web process type is run.

Here's an example `Procfile`:

    :::term
    web:    play run --http.port=$PORT $PLAY_OPTS

The `PLAY_OPTS` variable is used to set options that change from one environment to another. Now that you have a `Procfile`, you can start your application with the Foreman gem:

    :::term
    $ gem install foreman
    $ foreman start

Your app will come up on port 5000.  Test that it's working with curl or a web browser, then Ctrl-C to exit.

## Store Your App in Git

We now have the three major components of our app: dependencies in `dependencies.yml`, process types in `Procfile`, and our application source in `app/controllers/Application.java`.  Let's put it into Git:

    :::term
    $ git init
    $ git add .
    $ git commit -m "init"

# Deploy to Heroku/Cedar

Create the app on the Cedar stack:

    :::term
    $ heroku create --stack cedar
    Creating afternoon-frost-273... done, stack is cedar
    http://afternoon-frost-273.herokuapp.com/ | git@heroku.com:afternoon-frost-273.git
    Git remote heroku added

Deploy your code:

    :::term
    $ git push heroku master
    Counting objects: 33, done.
    Delta compression using up to 4 threads.
    Compressing objects: 100% (24/24), done.
    Writing objects: 100% (33/33), 36.17 KiB, done.
    Total 33 (delta 3), reused 0 (delta 0)

    -----> Heroku receiving push
    -----> play app detected
    -----> Installing Play!..... done
    -----> Building Play! application...
           ~        _            _ 
           ~  _ __ | | __ _ _  _| |
           ~ | '_ \| |/ _' | || |_|
           ~ |  __/|_|\____|\__ (_)
           ~ |_|            |__/   
           ~
           ~ play! 1.2.x-bfb715e, http://www.playframework.org
           ~
           1.2.x-bfb715e
           Resolving dependencies: .play/play dependencies ./ --forceCopy --sync --silent -Duser.home=/tmp/build_19mcxvj20b6cu 2>&1
           ~ Resolving dependencies using /tmp/build_19mcxvj20b6cu/conf/dependencies.yml,
           ~
           ~
           ~ No dependencies to install
           ~
           ~ Done!
           ~
           Precompiling: .play/play precompile ./ --silent 2>&1
           Listening for transport dt_socket at address: 8000
           22:50:04,601 INFO  ~ Starting /tmp/build_19mcxvj20b6cu
           22:50:05,265 INFO  ~ Precompiling ...
           22:50:08,635 INFO  ~ Done.

    -----> Built 1 Play! configuration(s).
    -----> Discovering process types
           Procfile declares types -> web
    -----> Compiled slug size is 26.2MB
    -----> Launching... done, v5
           http://afternoon-frost-273.herokuapp.com deployed to Heroku

Now, let's check the state of the app's processes:

    :::term
    $ heroku ps
    Process       State               Command
    ------------  ------------------  ------------------------------
    web.1         up for 1s           play run --http.port=$PORT $PLAY_O..

The web process is up.  Review the logs for more information:

    :::term
    $ heroku logs
    ...
    2011-08-20T22:50:14+00:00 heroku[web.1]: State changed from created to starting
    2011-08-20T22:50:14+00:00 heroku[slugc]: Slug compilation finished
    2011-08-20T22:50:16+00:00 heroku[web.1]: Starting process with command `play run --http.port=10800 --%prod -DusePrecompiled=true`
    2011-08-20T22:50:16+00:00 app[web.1]: 22:50:16,953 INFO  ~ Starting /app
    2011-08-20T22:50:17+00:00 app[web.1]: 22:50:17,011 INFO  ~ Precompiling ...
    2011-08-20T22:50:21+00:00 app[web.1]: 22:50:21,448 WARN  ~ Defaults messsages file missing
    2011-08-20T22:50:21+00:00 app[web.1]: 22:50:21,481 INFO  ~ Application 'helloworld' is now started !
    2011-08-20T22:50:21+00:00 app[web.1]: 22:50:21,538 INFO  ~ Listening for HTTP on port 10800 ...
    2011-08-20T22:50:22+00:00 heroku[web.1]: State changed from starting to up

Looks good.  We can now visit the app with `heroku open`. 

Note that the web page rendered on Heroku is slightly different from what you saw on your local build. That's because Heroku executes play in production mode by default. Use the `$PLAY_OPTS` environment variable to control the mode for your local build. You can also change the mode used on Heroku by modifying the `PLAY_OPTS` config var for your application.

## Further Reading

* [Building a Database-backed Play! application on Heroku/Cedar](/database-driven-play-apps)
* [Play! Framework documentation and tutorials](http://www.playframework.org/documentation)
