[![Continuous Integration](https://github.com/SmartWasteCollection/truck-microservice/actions/workflows/ci.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/truck-microservice/actions/workflows/ci.yml)
[![Continuous Integration](https://github.com/SmartWasteCollection/truck-microservice/actions/workflows/cd.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/truck-microservice/actions/workflows/cd.yml)
[![GitHub issues](https://img.shields.io/github/issues-raw/SmartWasteCollection/truck-microservice?style=plastic)](https://github.com/SmartWasteCollection/truck-microservice/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/SmartWasteCollection/truck-microservice?style=plastic)](https://github.com/SmartWasteCollection/truck-microservice/pulls)
[![GitHub](https://img.shields.io/github/license/e-scooter-2077/admin-api-gateway?style=plastic)](/LICENSE)
[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/SmartWasteCollection/truck-microservice?include_prereleases&style=plastic)](https://github.com/SmartWasteCollection/truck-microservice/releases)
[![codecov](https://codecov.io/gh/SmartWasteCollection/truck-microservice/branch/main/graph/badge.svg?token=UQUXZY4BEO)](https://codecov.io/gh/SmartWasteCollection/truck-microservice)

# truck-microservice

This repository contains the microservice that handles the management of trucks.

---

To run this microservice you can get the system's latest container image:

```
$ docker run -p 3001:3001 -e server.port=3001 ghcr.io/smartwastecollection/truck-microservice:<latestVersion>
```