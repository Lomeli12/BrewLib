BrewLib
===
Adds a custom brewing stand that modders can easily add brewing recipes to.

## Why make BrewLib?
1. As any modder will tell you, Minecraft's built in brewing recipe system sucks.
2. I don't really like the existing brewing APIs/Libraries out there. They're either bloated in that they try to do some hacky stuff to make using Minecraft's system easier to use, or they completely replace or make inaccessible the existing brewing stand, often breaking other mods that may use it.
3. There are no libraries/APIs for 1.8 that do this
4. NEI support for custom brewing recipes
5. I wanted to be able to use the OreDictionary in my brewing recipes.

## How to use BrewLib?

### Without packaging the API
**NOTE:** As I haven't finished the mod yet, the I haven't setup the maven repo yet. For now, just use the other method and I'll remove this notice once the maven repo is up.
1. Download the deobfuscated version from my website (https://lomeli12.net/) and add it as a dependency to your project in whatever IDE you prefer (I'd recommend either IntelliJ IDEA or Eclipse)
2. Add the following to your *build.gradle*
```
    repositories {
	    maven {
        	name = 'Lomeli Repo'
        	url = "http://lomeli12.net/maven/"
    	}
	}
	
	dependencies {
        //example:  compile "net.lomeli:BrewLib:{MCVersion}-{BrewLibVersion}:dev"
    	compile "net.lomeli:BrewLib:1.8-1.0.0:dev"
	}
```

### Packaging the API
1. Dowload the project's source (either using *git* or the convient *"Download ZIP"* button located on the right)
2. Include the `net.lomeli.brewlib.api` package and it's contents with your source.