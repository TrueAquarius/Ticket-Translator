# Ticket-Translator
(Maturity Level: Proof Of Concept)

This application performs **language translations** of tickets of a **Ticket System** such as *Jira* or *Service Now*.

(Currently, only *Jira* is supported; however the application can easily be extended to support other ticket systems.)

The acutlal translation is performed usig a Translation Service (such as Azure Translate).

![System Landscape Overview](/Documentation/Images/Landscape.png)

## How it works
The translation process to perforemed in the following steps.
1. User enters a new ticket or modified an existing ticket in the **Ticket System**.
2. The **Ticket System** is configured to trigger a *Web Hook* which calls this **Ticket-Translator** indicating the need for translation. The Web Hook Call includes the *TicketID* as well as an ID of the **Ticket Systems** (*TicketSystemID*).
3. The **Ticket-Translatior** fetches the ticket details from the **Ticket System** (API Call call to Ticket System).
4. The **Ticket-Translator** translates the ticket using a **Translation Service** (API call to Traslation Service).
5. The **Ticket-Translator** updates the ticket in the **Ticket System** (API call to Ticket System).

![System Landscape Overview](/Documentation/Images/SequenceDiagram.png)

## Technology Stack
* Java (Version 17)
* Java Spring Boot
* Gson (Google Json)
* Ticket System: Jira
* Translation: Azure Translate
* Database: MongoDB


