I used Github Copilot to assist with this project,
but not my AI interaction log
or reflection. 

I used the prompts for each method to analyze the methods, it provided analysis, but it also reads every file.
Because it reads every file, each time I used the prompts it seemed to address both the analysis and implementation
at the same time. I typically accepted the suggestions for both. There was a pattern of replacing the broader catch (Exception e)
with IllegalArgumentException for null collection and EmptyCollectionException for empty ones.
This is for more robust code and easier debugging. I also wrapped unexpected processing 
failures into InvalidDataException, but it recommended to always include a cause.

In the performance review, it noted that the in the sorting and filtring
methods, sorting happens before data is reduced or sorts the entire 
collection when only top 10 is needed for example. This approach is 
fine for small datasets (~1000 elements in a list), but for large data
sets, reordering and cleaning data before sorting is typically faster.
In this case, I chose to leave as is because the test data is small.
However, if this was deployed in production with large datasets, I
would highly consider an approach that did not sort the entire collection
and/or reduced the data first and take into consideration other constraints
in the project.

In a review of the methods, it suggested creating a custom
exception and utilizing that in an attempt to be more
detailed about the root cause. It looked similar to
the original exception handling I had, but I accepted it to
see the difference and it errored all of my tests.
Because the messaging seemed similar, previous code
passed tests I rolled the changes back. 

For the missing test cases and edge cases, the recommendation
is heavily focused on filling in the placeholder tests. 
When I accepted placeholder changes, and it looks like
the tests still pass.



