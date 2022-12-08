Text-Analyzer
============

A text-analyzer java implementation for Data Structures and Algorithms class using Red-black trees, Hash Maps, AVL trees, Sets and graphs.

## Getting Started

### Prerequisites

_In order to execute and compile the project, you need to have installed `maven`_.

* dnf
  ```sh
  dnf install maven
  ```

### Installation

1. Clone repository
   ```sh
   git clone https://github.com/julianrosas11032002/text-analyzer.git
   ``` 
   
   ```sh
   cd text-analyzer
   ```
2. Install .jar (using maven) by doing:
   ```sh
   mvn install
   ```
   _For a succesful installation, you need to place in the `text-analyzer` directory (where the `pom.xml` file is) and execute the command above._
   
## Usage

After a succesful installation, (placing in `text-analyzer` directory) execute the .jar by doing:

```sh
java -jar target/proyecto3.jar [A] -o [B]
```
Where:

[A] - Files to Analyse.

[B] - Name of the directory where the text analysis is going to be stored, it must be after `-o` flag.

**NOTE: The `-o` flag can be anywhere in the proyecto3.jar arguments, however, it must be specified the directory where the analysis is going to be stored, otherwise, the program will terminate.**

After execution, change the current directory to the specified one by the program (`[B]`) and execute the `index.html` file in order to check the analysis.

Within the `index.html` file there is a **Graph** which connects two analysed files if and only if both files have in common 7 words with at least 7 characters. Moreover, you can navigate to each of the analysed files by clicking on their names.

Each Analysed file (stated by `[A]`) has:

1. An SVG generated **Red-Black tree** of the 15 most repetitive words within the file.
2. An SVG generated **AVL tree** of the same information.
3. An SVG generated **Bar Chart** of the 20 most repetitive words and their respective percentage among the total words.
4. An SVG generated **Pie Chart** with the same information.
5. The total repetitions of each word in the file.

## Contact

Julián Rosas Scull - julian.rosas@ciencias.unam.mx

Project Link: [https://github.com/julianrosas11032002/text-analyzer](https://github.com/julianrosas11032002/Text-Analyzer)
















   
   
   




