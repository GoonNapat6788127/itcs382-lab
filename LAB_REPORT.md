# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/GoonNapat6788127/itcs382-lab.git

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Oracle JDK 23.0.2 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.142.0 |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | ChatGPT GPT-6|

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S2143 | ParcelService.java:[Ln 1, Col 1] | Use the "java.time" API for date and time. |
| 2 | java:S1128 | ParcelService.java:[Ln 6, Col 8] | Remove this unused import 'java.util.Date'. |
| 3 | java:S1068 | ParcelService.java:[Ln 12, Col 17] | Remove this unused "maxParcels" private field. |
| 4 | java:S106 | ParcelService.java:[Ln 30, Col 17] | Replace this use of System.out by a logger. |
| 5 | java:S4973 | ParcelService.java:[Ln 42, Col 52] | Strings and Boxed types should be compared using "equals()". |
| 6 | java:S106 | ParcelService.java:[Ln 67, Col 17] | Replace this use of System.out by a logger. |
| 7 | java:S106 | ParcelService.java:[Ln 69, Col 17] | Replace this use of System.out by a logger. |
| 8 | java:S2095 | ParcelService.java:[Ln 76, Col 33] | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 9 | java:S108 | ParcelService.java:[Ln 80, Col 33] | Remove this block of code, fill it in, or add a comment explaining why it is empty. |
| 10 | java:S106 | Main.java:[Ln 9, Col 9] | Replace this use of System.out by a logger. |
| 11 | java:S106 | Main.java:[Ln 15, Col 17] | Replace this use of System.out by a logger. |
| 12 | java:S106 | Main.java:[Ln 17, Col 17] | Replace this use of System.out by a logger. |
| 13 | java:S106 | Main.java:[Ln 22, Col 17] | Replace this use of System.out by a logger. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 20 October 2026 |
| AI tool and exact model | ChatGPT GPT-6 |
| Commit reviewed | 236b662 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | attached the files |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:11 | `split(" ")` mishandles repeated spaces, and blank input causes indexing errors | AI | Code quality (input handling) | Yes. Repeated spaces do create empty entries in `words`. Blank input does not crash: `"".split(" ")` gives one empty string, so `words[0]` exists and no command matches. |
| 2 | Main.java:13, 17 | No check of the argument count or of the numbers | AI | Code quality (input validation) | Yes. `store P1` reads `words[2]` and throws `ArrayIndexOutOfBoundsException`. `pickup P1 x` throws `NumberFormatException`. |
| 3 | ParcelService.java:42 | Strings compared with `==` in `pickUp` | both | java:S4973 | Yes. `parcel.getCode() == code` compares references, which is why `pickup P1 5` did nothing in step C2. |
| 4 | ParcelService.java:76 | `FileWriter` is never closed | both | java:S2095 | Yes. `writer` is never closed or flushed, which is why `parcels.csv` was empty in step C2. |
| 5 | ParcelService.java:80 | Empty `catch` block hides write errors | both | java:S108 | Yes. The `catch (IOException e)` block on lines 80 to 81 has no code. |
| 6 | ParcelService.java:6 | Unused import `java.util.Date` | both | java:S1128 | Yes. Line 6 imports `Date` and nothing in the file uses it. |
| 7 | ParcelService.java:12 | Unused field `maxParcels`, so the limit of 100 parcels is never enforced | both | java:S1068 | Yes. The field is assigned `100` and never read anywhere. |
| 8 | ParcelService.java:28, 30, 44, 68 | Status values and notification text are hard-coded | AI | Code quality (hard-coded strings) | Yes. The status strings `"STORED"` (lines 28 and 68) and `"PICKED_UP"` (line 44) are hard-coded. The notification text at line 30 appears only once, so that part is weak. |
| 9 | ParcelService.java:52-62 | `fee()` charges the largest rate for every size other than 1 and 2, and nothing validates the size | AI | Code quality (missing validation) | Yes. Size 0 or 99 falls through to the last `return` at 20.0 per day, and the `Parcel` constructor accepts any int. |
| 10 | Main.java:6-26 | `main()` does input parsing, command dispatch, object creation and output in one method | AI | Code quality (long method) | No. It has 22 lines and no deep nesting. That is not long, and SonarQube’s long-method and complexity thresholds are much higher. |
| 11 | ParcelService.java:24-37 | `storeParcel()` selects a locker, updates state, prints the SMS and saves the file | AI | Code quality (long method) | Yes. Lines 26 to 33 do all of these in one method. |
| 12 | ParcelService.java:39-50 | `pickUp()` finds the parcel, changes state, saves the file and calculates the fee | AI | Code quality (long method) | Yes. Lines 41 to 46 do all of these in one method. |
| 13 | Parcel.java:7 | Public field `status` | AI | Code quality (Non-private field) | Yes. `public String status;` is written directly from `ParcelService` at lines 28 and 44. |
| 14 | ParcelService.java:20-22 | `getLockers()` returns the internal list, so callers can change it | AI | Code quality (exposed internal list) | Yes. It returns the real `lockers` list. `Main` only counts free lockers (line 22), but any caller could add or remove lockers. |
| 15 | Locker.java:4-6, Parcel.java:4-6 | Fields are mutable although most are never updated | AI | Code quality (immutability) | No, `number`, `code`, `studentId`, and `size` are included. Plain `private` fields are normal here, and `status` and `Locker.parcel` can change. It is a style preference, not a defect. |
| 16 | Main.java:6-26 | `main()` sequences input, branching, object creation and output | AI | Procedural cohesion | Yes. One method does several steps in order without one narrow purpose. |
| 17 | ParcelService.java:24-37 | `storeParcel()` runs locker assignment, notification and file saving in a fixed sequence | AI | Procedural cohesion | Yes. Lines 26 to 33 run these steps in order and nothing ties them into one job. |
| 18 | ParcelService.java:39-50 | `pickUp()` runs lookup, state change, saving and fee calculation in sequence | AI | Procedural cohesion | Yes. Lines 41 to 46 do all of these in sequence. |
| 19 | ParcelService.java:64-72 | `printParcels()` does two different listings depending on `forStaff` | AI | Logical cohesion | Yes. The flag chooses between two different outputs on lines 66 to 70. |
| 20 | ParcelService.java:9-12, 24-82 | `ParcelService` handles lockers, parcels, fees, printing, SMS output and file writing | AI | Class cohesion (low; visible methods are not functional) | Yes. `fee()` uses none of the class's fields, and printing, notifying and saving are separate jobs. |
| 21 | Main.java:19, 21 | `Main` passes a boolean to choose the behaviour inside `printParcels` | AI | Control coupling | Yes. Line 19 passes `false` and line 21 passes `true` to select the branch. |
| 22 | Main.java:5-22 | `Main` depends on `ParcelService`, `Parcel`, `Locker` and `Scanner` | AI | The Law of Demeter coupling | Yes. `Main` builds `Parcel` objects (line 13), calls `Locker` methods (lines 14 and 22) and reaches through `service.getLockers()` on line 22. |
| 23 | Main.java:13, 17 | Command arguments are read by array position (`words[1]`, `words[2]`, `words[3]`) | AI | Connascence of position | Yes. The parsing depends on the order the user types the arguments, and the same order is repeated for `store` and `pickup`. |
| 24 | Parcel.java:13, ParcelService.java:28, 44, 68 | Status strings `"NEW"`, `"STORED"` and `"PICKED_UP"` must match exactly in several places | AI | Connascence of convention | Yes. `"STORED"` at line 28 must match line 68, so a typo breaks `list`. |
| 25 | Locker.java:5, Parcel.java:6, ParcelService.java:15-17, 26, 56-61 | The numbers 1, 2 and 3 mean small, medium and large in several classes | AI | Connascence of convention | Yes. `Locker` and `Parcel` both store the size, line 26 compares them, and `fee()` branches on the same numbers. |
| 26 | ParcelService.java:1 | Use the java.time API instead of the old Date class | SonarQube | java:S2143 | Yes, but redundant. The rule fires only because `java.util.Date` is imported and never used, so removing that import (row 6) also clears it. |
| 27 | ParcelService.java:30 | The SMS message in `storeParcel` is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 30 prints the SMS text directly. Low severity for a command-line program, but it mixes output into the service class. |
| 28 | ParcelService.java:67 | The staff listing in `printParcels` is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 67 prints the code, student ID and status directly. Same low severity as row 27. |
| 29 | ParcelService.java:69 | The "is waiting" listing in `printParcels` is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 69 prints the waiting message directly. Same low severity as row 27. |
| 30 | Main.java:9 | The list of commands is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 9 prints the "Commands: ..." help text directly. Low severity, because the help text is part of a command-line program's interface. |
| 31 | Main.java:15 | The result of `store` ("Locker N" or "No free locker") is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 15 prints the result of the store command. Same low severity as row 30. |
| 32 | Main.java:17 | The fee of `pickup` is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 17 prints the fee returned by `pickUp`. Same low severity as row 30. |
| 33 | Main.java:22 | The number of free lockers is printed with `System.out.println` instead of a logger | SonarQube | java:S106 | Yes. Line 22 prints the free-locker count after `staff`. Same low severity as row 30. |

2.1 Name two problems that only the AI review found.

1. Finding 13. Public field `status`. The field is declared `public String status;`, so ParcelService changes it directly at lines 28 and 44 and Parcel cannot control its own state.

2. Finding 14. `getLockers()` returns the internal list. It returns the real `lockers` list, so any caller can add or remove lockers without ParcelService knowing.

2.2 Name one problem that only SonarQube found, or write none.

1. Finding 16. The use of the old `java.util.Date` API. The AI review did not mention it. The finding is real but of low value: `Date` is imported and never used, so the rule fires only because of the unused import.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

1. Finding 15 was too vague and overstated. It lumps six fields together as one problem, but they are not the same case. `Locker.number`, `Locker.size`, `Parcel.code`, `Parcel.studentId` and `Parcel.size` are never reassigned after the constructor, while `Locker.parcel` must stay mutable because `setParcel` changes it at lines 27 and 43 of ParcelService. The finding also gives no consequence, so it is a style preference (marking fields `final`) and not a defect that makes the program behave wrongly. I checked it by opening both files at those lines and searching for every assignment to each field.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | | |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
