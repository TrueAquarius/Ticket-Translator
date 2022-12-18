# Ticket Translator
(Maturity Level: Proof Of Concept)

This application performs **language translations** of tickets of a **Ticket System** such as *Jira* or *Service Now*.

(Currently, only *Jira* is supported; however the application can easily be extended to support other ticket systems.)

The acutlal translation is performed usig a Translation Service (such as Azure Translate).

![System Landscape Overview](/Documentation/Images/Landscape.png)

## How it works
The translation process to perforemed in the following steps.
1. User enters a new ticket or modified an existing ticket in the **Ticket System**.
2. The **Ticket System** is configured to trigger a *WebHook* which calls this **Ticket-Translator** indicating the need for translation. The *WebHook* Call includes the *TicketID* as well as an ID of the **Ticket Systems** (*TicketSystemID*).
3. The **Ticket Translator** fetches the ticket details from the **Ticket System** (API Call call to Ticket System).
4. The **Ticket Translator** translates the ticket using a **Translation Service** (API call to Traslation Service).
5. The **Ticket Translator** updates the ticket in the **Ticket System** (API call to Ticket System).

![System Landscape Overview](/Documentation/Images/SequenceDiagram.png)

## Technology Stack
* Java (Version 17)
* Java Spring Boot
* Gson (Google Json)
* Ticket System: Jira
* Translation: Azure Translate
* Database: MongoDB



## Configuration
### Configure Jira
You have to configure Jira to trigger *WebHook* Calls after a new ticket is created and after changes to an existing ticket are made. Log into *Jira* as Administrator, go to System Administration (see screen shot).

![Jira Admin](/Documentation/Images/JiraAdmin.png)

Select *WebHooks* in the menu on the left side (not shown in screen shot).

Configure WebHook as shown on screen shot below.
* Name: Any name which describes the purpose of the WebHook.
* Status: Enabled
* URL: <code>http://*\<server\>*:*\<port\>*/api/v1/translate/*\<TicketSystemID\>*/${issue.key}</code>.<br />
  The *TickerSystemID* distinguishes your *Ticket System* from other *Ticker Systems* which may also use the same *Ticket Translator*.
* Description: A nice description of your choice.
* Issue related events: You may want to use the JQL Query to filter Tickets from a particular project (or other criteria) to trigger this *WebHook*.
* Issue: select "create" and "update".

![Jira WebHook](/Documentation/Images/JiraWebHooks.png)





