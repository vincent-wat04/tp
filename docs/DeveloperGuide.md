---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# MeetCLI Developer Guide

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** above explains the high-level design of the App.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-F15A-3/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-F15A-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

(… keep existing architecture/logic/UI/storage sections as they are, just replacing AB3 → MeetCLI when needed …)

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

### Next meeting field

The next meeting information is modelled as a dedicated value object (`seedu.address.model.person.NextMeeting`) so that validation, defaults, and equality checks stay consistent across the code base.

**Parsing workflow**
- `CliSyntax.PREFIX_NEXT_MEETING (m/)` marks the optional input.
- `AddCommandParser` falls back to `NextMeeting.DEFAULT_VALUE` (`"No meeting scheduled"`) when the prefix is absent, while `ParserUtil.parseNextMeeting` trims and validates non-empty input.
- `EditCommandParser` maps the prefix into `EditCommand.EditPersonDescriptor`, enabling partial updates during `edit`.

**Model and storage**
- `Person` stores a `NextMeeting` instance alongside existing identity and data fields.
- `JsonAdaptedPerson` serialises/deserialises the value, substituting the default string when the JSON omits the field so older save files continue to load.
- `SampleDataUtil` provides illustrative values so that the UI demonstrates the field out of the box.

**UI**
- `PersonCard` now renders a `nextMeeting` label inside `PersonListCard.fxml`, showing `Next meeting: <value>` so users can see at-a-glance follow-up details.

> **Scope decision (v1.5):** We experimented with a richer `Meeting` aggregate in v1.4 but removed it after integration issues. Keeping `NextMeeting` as a single value object lets us ship a reliable reminder while we refine the structured design for a later release.

Automated coverage lives in `AddCommandParserTest`, `EditCommandParserTest`, `ParserUtilTest`, and `PersonTest`, ensuring the prefix behaves correctly, invalid values are rejected, and equality semantics include the new field.

### Tag commands (`addtag` and `listtag`)

MeetCLI extends the base AddressBook3 logic to support tag management commands.  
Users can now add new tags to specific contacts and view all existing tags in alphabetical order.

<puml src="diagrams/LogicTagCommandsClassDiagram.puml" width="600" />

The diagram above illustrates how the new `AddTagCommand` and `ListTagCommand` integrate with the existing architecture:
- Both inherit from the abstract `Command` class.
- `LogicManager` orchestrates their execution via the `Model` interface.
- `ModelManager` implements the tag-related operations, delegating to the `TagRegistry` and `Person` classes.
- `AddTagCommand` updates a contact’s tag list, while `ListTagCommand` queries and aggregates unique tags.

This separation maintains the command pattern used throughout the app and keeps the tag functionality modular and testable.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
* NUS students or working professionals who need to manage a significant number of contacts
* Prefer desktop apps over mobile/web apps
* Can type fast and prefer CLI over GUI
* Want to group contacts by tags (e.g., modules, CCAs, work projects)
* Comfortable with keyboard-based apps

**Value proposition**: Manage and retrieve contacts faster than typical GUI apps, with features tailored for grouping, filtering, and organizing by context (academic modules, CCAs, or work projects).

---

### User stories

Priorities: High (must have) - `***`, Medium (nice to have) - `**`, Low (unlikely to have) - `*`

| Priority | As a …​                | I want to …​                           | So that I can …​                           |
|----------|-------------------------|-----------------------------------------|--------------------------------------------|
| `***`    | new user                | see usage instructions                  | learn how to use the app quickly            |
| `***`    | user                    | add a new contact                       | keep track of people I know                 |
| `***`    | user                    | delete a contact                        | remove outdated or irrelevant entries       |
| `***`    | user                    | search for a contact by name            | find details quickly                        |
| `***`    | user                    | tag contacts by group/module            | organize contacts into categories           |
| `**`     | student                 | filter contacts by tag                  | view only project/module teammates          |
| `**`     | user with many contacts | sort contacts by name                   | locate people easily                        |
| `**`     | busy user               | import/export contacts from a file      | avoid retyping data                         |
| `*`      | user                    | set reminders for specific contacts     | remember to follow up                       |

---

### Use cases

#### Use case: Delete a contact

**MSS**
1. User requests to list all contacts.
2. MeetCLI shows the list of contacts.
3. User requests to delete a specific contact in the list.
4. MeetCLI deletes the contact.
   * Use case ends.

**Extensions**
* 2a. The list is empty → Use case ends.
* 3a. The given index is invalid → Error shown, return to step 2.

---

#### Use case: Tag contacts by module

**MSS**
1. User requests to add a tag (e.g., “CS2103T”) to a specific contact.
2. MeetCLI updates the contact with the new tag.
3. Updated contact is shown in the contact list.
   * Use case ends.

**Extensions**
* 1a. Tag already exists → App prevents duplication.
* 1b. Invalid tag format → Error shown.

---

### Non-Functional Requirements

1. Should work on any mainstream OS (Windows, Linux, macOS) with Java 17+.
2. Should handle up to 1,000 contacts without noticeable performance issues.
3. A user with above-average typing speed should be able to complete core tasks faster using CLI than with a GUI-based app.
4. Data should be saved automatically after any modification.
5. The app should start up within 2 seconds on a typical laptop.
6. The system should be extensible for future modules/features (e.g., linking contacts to events).

---

### Glossary

* **CLI**: Command Line Interface.
* **Contact**: A record representing a person with fields such as name, phone, and email.
* **Tag**: A label assigned to contacts for grouping (e.g., “CS2103T”, “Family”).
* **Command**: A textual instruction input by the user.
* **Module tag**: A tag representing an academic module (e.g., “CS2103T”) used to group contacts.

--------------------------------------------------------------------------------------------------------------------
