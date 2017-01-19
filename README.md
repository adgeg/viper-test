# Clean Login
A *clean* implementation of a login use-case, following [recommendations of Uncle Bob] (https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html):
* Independent of Frameworks: **no RxJava/Android**, **not even Dagger**, all business rules, located in the core module, are in pure Java. Libraries (Jackson, AutoValue, Databinding) are used as tools by the implementations of interfaces defined in the core module
* Testable: core, presentation and repositories modules have been implemented test-first
* Independent of UI : core module is a Java module, **without any reference to Android**

Asynchronism is handled on one side (Activity --> Controller) by an asynchronous decorator on the Controller. And on the other side (Presenter --> Activity) by a features of the Databinding library which allows to update the binding from outside the UI thread.

PS: Contrary to my original thought, this is not a [Viper architecture](https://www.objc.io/issues/13-architecture/viper), even if there are many shared patterns. Indeed, the outputPort is not the entry point to the business logic. It is simply an output port, [as defined by Robert Martin](https://8thlight.com/blog/assets/posts/2012-08-13-the-clean-architecture/CleanArchitecture-8b00a9d7e2543fa9ca76b81b05066629.jpg). And there is no notion of routing here.
