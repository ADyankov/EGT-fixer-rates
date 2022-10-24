# EGT-fixer-rates
Collect exchange rate info from fixer.io

Instructions:

Currencies can be added via application.properties file -> rates.latest.base. This field accepts comma separated currencies (e.g. USD,EUR,BGN)
API key for the fixer.io API can be assigned via application.properties file -> rates.api-key
The delay between requests for currency updates can be adjusted via application.properties file -> rates.poll.rate (in milliseconds)
