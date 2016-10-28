/**
 * Represents an object LocalDate of java8
 */
interface LocalDate {
    year: number;
    month : string;
    monthValue: number;
    dayOfMonth: number;
}

export class DateUtil {

    static formatLocalDate(date: LocalDate, format: string) {
        if (!date) {
            return "";
        }

        var bits = <any>[];

        function add(s: any) {
            bits.push(s);
        }

        function skipCharacters(n: any) {
            format = format.slice(n);
        }

        while (format.length) {
            switch (format.charAt(0)) {
                case 'y':
                    if (/^yyyy/.test(format)) {
                        add(date.year);
                        skipCharacters(4);
                        continue;
                    }
                    skipCharacters(1);
                    continue;
                case 'M':
                    if (/^MMMM/.test(format)) {
                        add(date.month);
                        skipCharacters(4);
                        continue;
                    }
                    if (/^MMM/.test(format)) {
                        add(date.month.substring(0, 3));
                        skipCharacters(3);
                        continue;
                    }
                    if (/^MM/.test(format)) {
                        if (date.monthValue < 10) {
                            add("0" + date.monthValue);
                        } else {
                            add(date.monthValue);
                        }
                        skipCharacters(2);
                        continue;
                    }
                    add(date.monthValue);
                    skipCharacters(1);
                    continue;
                case 'd':
                    if (/^dd/.test(format)) {
                        if (date.dayOfMonth < 10) {
                            add("0" + date.dayOfMonth);
                        } else {
                            add(date.dayOfMonth);
                        }
                        skipCharacters(2);
                    }
            }
            add(format.charAt(0));
            skipCharacters(1);
        }
        return bits.join("");
    }
}