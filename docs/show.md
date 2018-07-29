# Show

Show provides a textual representation. You have to put an annotation on your case class.

```scala
@ShowMacro
case class Name(firstName: String, lastName: String)

val n = Name("john", "doe")
Show.show(n) // "Name(firstName=john,lastName=doe)
```


It also can be a nested case class like (but all the cases classes must be annotated): 

```scala 
@ShowMacro
case class Name(firstName: String, lastName: String)

@ShowMacro
case class Person(name: Name, age: Int)

val p = Person(Name("john", "doe"), 35)
Show.show(p) // "Person(name=Name(firstName=john,lastName=doe),age=35)"

// ShowableSyntax add show method
import ShowableSyntax.ShowableOps
Person(Name("john", "doe"), 35).show // "Person(name=Name(firstName=john,lastName=doe),age=35)"
```


It also works on case classes, case objects: 

```scala
Show.show(Name("hamster", "hiden").getClass) // "Name"

case object Foo
Show.show(Foo.getClass) // "Foo"
```

## Clazz 

Clazz provides string representation of class type.

```scala
import io.github.hamsters.Clazz

class Foo
case class Bar(x : String)
case object Quix

Clazz.getSimpleName[Foo] // Foo
Clazz.getSimpleName[Bar] //Bar
Clazz.getSimpleName[Quix.type ] //Quix
```

## Depedencies 

To use `Show` macro, you need to add this dependencies to your build: 

```scala
libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided
addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full)
```
