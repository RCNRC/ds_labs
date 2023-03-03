#!/bin/sh

# Создаёт 50000 записей syslog файла (каждая с шансом в 10% - недопустимая) в каждом из 10 файлов ввода данных.

tmp_string="nothing"
declare -a months=("Jan" "Feb" "Mar" "Apr" "May" "Jun" "Jul" "Aug" "Sep" "Oct" "Nov" "Dec")
declare -a words=("sadas" "some words" "here i am" "walking" "to:sd daswqd" "panic" "gtergerg" "WEFaf aerg ae rg" "aergaergaer" "ager jytuykuigl  hsd hv" "earawfD Sdsd")

function make_valid_string(){
	pri=$(($RANDOM%192))
	month=${months[$RANDOM%${#months[@]}]}
	day=$(($RANDOM%32))
        if [ $day -lt 10 ]
        then
                day="0$day"
        fi
	hours=$(($RANDOM%24))
        if [ $hours -lt 10 ]
        then
                hours="0$hours"
        fi
	minutes=$(($RANDOM%60))
        if [ $minutes -lt 10 ]
        then
                minutes="0$minutes"
        fi
	seconds=$(($RANDOM%60))
	if [ $seconds -lt 10 ]
	then
		seconds="0$seconds"
	fi
	IP1=$(($RANDOM%256))
	IP2=$(($RANDOM%256))
	IP3=$(($RANDOM%256))
	IP4=$(($RANDOM%256))
	log_message=${words[$RANDOM%${#words[@]}]}
	tmp_string="<$pri>$month $day $hours:$minutes:$seconds $IP1.$IP2.$IP3.$IP4 $log_message"
}

function make_invalid_string(){
        pri=$(($RANDOM%300))
        month=${months[$RANDOM%${#months[@]}]}
	month="$month"
        day=$(($RANDOM%56))
        if [ $day -lt 10 ]
        then
                day="+$day"
        fi
        hours=$(($RANDOM%44))
        if [ $hours -lt 10 ]
        then
                hours="-$hours"
        fi
        minutes=$(($RANDOM%90))
        if [ $minutes -lt 10 ]
        then
                minutes="=$minutes"
        fi
        seconds=$(($RANDOM%90))
        if [ $seconds -lt 10 ]
        then
                seconds=")$seconds"
        fi
        IP1=$(($RANDOM%2056))
        IP2=$(($RANDOM%1056))
        IP3=$(($RANDOM%556))
        IP4=$(($RANDOM%556))
        log_message=${words[$RANDOM%${#words[@]}]}
        tmp_string="<$pri>$month $day $hours:$minutes:$seconds $IP1.$IP2.$IP3.$IP4 $log_message"
}

rm -rf ./input
mkdir input

for i in {1..10}
do
touch "./input/input.$i"
	for j in {1..50000}
	do
		choise=$(($RANDOM%100))
		if [ $choise -gt 90 ]
        	then
			make_valid_string
		else
			make_invalid_string
        	fi
		echo "$tmp_string" >> "./input/input.$i"
	done
done

echo "Recreated input data."
