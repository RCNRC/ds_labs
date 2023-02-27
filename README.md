# ds_labs
data science labs
Полностью основано на уроках из плейлиста [Big Data Technologies](https://www.youtube.com/playlist?list=PLGESve048UzTbAqDil1EfsU4Lre3B09bt).

## Предварительная установка

1. Скачайте и установите [CentOS7](http://isoredirect.centos.org/centos/7/isos/x86_64/). Требуется не меньше 6 ГБ выделенной под ОС оперативной памяти и не менее 60 ГБ памяти на жёстком диске. Во время установки создайте себе простого пользователя. После установки обновите ОС через программу "Software Update" и перезагрузите систему.
    - Лучше всего установить на виртуальную машину, например [VMware Workstation Player](https://www.vmware.com/products/workstation-player.html).
2. В самой системе CentOS7 скачайте и установите [hadoop](https://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.10.2/hadoop-2.10.2.tar.gz) и [JDK](https://www.oracle.com/cis/java/technologies/downloads/).

## Предварительная настройка

Для развёртывания псевдо распределённой системы на локальном сервере необходимо пройти все шаги из [документации](https://hadoop.apache.org/docs/r2.10.2/hadoop-project-dist/hadoop-common/SingleCluster.html#Pseudo-Distributed_Operation).
