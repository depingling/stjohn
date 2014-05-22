function scwSetLanguage()
        {switch (scwLanguage)
            {case 'ar':
                // Spanish[Castellano/Argentine] (provided by Sebastian Vega)
                scwToday               = 'Hoy:';
                scwDrag                = 'click aqu\u00ED para arrastrar';
                scwArrMonthNames       = ['Ene','Feb','Mar','Abr','May','Jun',
                                          'Jul','Ago','Sep','Oct','Nov','Dec'];
                scwArrWeekInits        = ['D','L','M','M','J','V','S'];
                scwInvalidDateMsg      = 'La fecha ingresada es inv\u00E1lida.\n';
                scwOutOfRangeMsg       = 'La fecha ingresada est\u00E1 fuera de rango.';
                scwDoesNotExistMsg     = 'La fecha ingresada no existe.';
                scwInvalidAlert        = ['Fecha inv\u00E1lida (',') ignorada.'];
                scwDateDisablingError  = ['Error ',' no es un objeto Fecha.'];
                scwRangeDisablingError = ['Error ',' deber\u00EDa consistir de dos elementos.'];
                break;

             case 'br':
                // Brazilian Portuguese (provided by Rafael Pirolla)
                scwToday               = 'Hoje:';
                scwDrag                = 'clique aqui para arrastar';
                scwArrMonthNames       = ['Jan','Fev','Mar','Abr','Mai','Jun',
                                          'Jul','Ago','Set','Out','Nov','Dez'];
                scwArrWeekInits        = ['D','S','T','Q','Q','S','S'];
                scwInvalidDateMsg      = 'A data e invalida.\n';
                scwOutOfRangeMsg       = 'A data esta fora do escopo definido.';
                scwDoesNotExistMsg     = 'A data nao existe.';
                scwInvalidAlert        = ['Data invalida (',') ignorada.'];
                scwDateDisablingError  = ['Erro ',' n\u00E3o \u00E9 um objeto Date.'];
                scwRangeDisablingError = ['Erro ',' deveria consistir de dois elementos.'];
                break;

			case 'cr':
				// Croatian (provided by Amir Dupanović)
				scwToday               = 'Danas:';
				scwDrag                = 'klikni ovdje za povla\u010denje';
				scwArrMonthNames       = ['Sij','Velj','O\u017eu','Tra','Svi','Lip', 
					                      'Srp','Kol','Ruj','Lis','Stu','Pro'];
				scwArrWeekInits        = ['N','P','U','S','\u010c','P','S'];
				scwInvalidDateMsg      = 'Une\u0161eni datum nije ispravan.\n';
				scwOutOfRangeMsg       = 'Une\u0161eni datum je van granica.';
				scwDoesNotExistMsg     = 'Une\u0161eni datum je nepostoje\u0107i.';
				scwInvalidAlert        = ['Neispravan datum (',') ignoriran.'];
				scwDateDisablingError  = ['Pogre\u0161ka ',' nije Date objekt.'];
				scwRangeDisablingError = ['Pogre\u0161ka ',' se mora sastojati od dva elementa.'];
				break;
					
            case 'cz':
				// Czech (provided by Ondrej Lisy)
                scwToday               = 'Dnes:';
                scwDrag                = 'Pro p\u0159esun klikn\u011bte zde';
                scwArrMonthNames       = ['Led','\u00dano','B\u0159e','Dub','Kv\u011b','\u010cer',
                                          '\u010cer','Srp','Z\u00e1\u0159','\u0158\u00edj','Lis','Pro'];
                scwArrWeekInits        = ['N','P','\u00da','S','\u010c','P','S'];
                scwInvalidDateMsg      = 'Zadan\u00e9 datum nen\u00ed platn\u00fd.\n';
                scwOutOfRangeMsg       = 'Zadan\u00e9 datum je mimo rozsah.';
                scwDoesNotExistMsg     = 'Zadan\u00e9 datum neexistuje.';
                scwInvalidAlert        = ['Neplatn\u00fd datum (',') ignorov\u00e9n.'];
                scwDateDisablingError  = ['Chyba ',' nen\u00ed objektem typu Date.'];
                scwRangeDisablingError = ['Chyba ',' mus\u00ed obsahovat dva elementy.'];
                break;
				
            case 'de':
                // German (provided by Henning Hraban Ramm)
                scwToday               = 'Heute:';
                scwDrag                = 'zum Ziehen hier klicken';
                scwArrMonthNames       = ['Jan','Feb','M\u00E4r','Apr','Mai','Jun',
                                          'Jul','Aug','Sep','Okt','Nov','Dez'];
                scwArrWeekInits        = ['S','M','D','M','D','F','S'];
                scwInvalidDateMsg      = 'Das eingegebene Datum ist ung\u00FCltig.\n';
                scwOutOfRangeMsg       = 'Das eingegebene Datum liegt au\u00DFerhalb der gesetzten Grenzen.';
                scwDoesNotExistMsg     = 'Das eingegebene Datum gibt es nicht.';
                scwInvalidAlert        = ['Ung\u00FCltiges Datum (',') ignoriert.'];
                scwDateDisablingError  = ['Fehler ',' ist kein Datumsobjekt.'];
                scwRangeDisablingError = ['Fehler ',' muss aus zwei Elementen bestehen.'];
                break;

            case 'es':
                // Spanish (provided by Victor Davalos)
                scwToday               = 'Hoy:';
                scwDrag                = 'click aqu\u00ED para arrastrar';
                scwArrMonthNames       = ['Ene','Feb','Mar','Abr','May','Jun',
                                          'Jul','Ago','Sep','Oct','Nov','Dic'];
                scwArrWeekInits        = ['D','L','M','M','J','V','S'];
                scwInvalidDateMsg      = 'La fecha ingresada es inv\u00E1lida.\n';
                scwOutOfRangeMsg       = 'La fecha ingresada est\u00E1 fuera de rango.';
                scwDoesNotExistMsg     = 'La fecha ingresada no existe.';
                scwInvalidAlert        = ['Fecha inv\u00E1lida (',') ignorada.'];
                scwDateDisablingError  = ['Error ',' no es un objeto Fecha.'];
                scwRangeDisablingError = ['Error ',' deber\u00EDa consistir de dos elementos.'];
                break;

			case 'fi':
				// Finnish (provided by Esa Ristilä)
				scwToday               = 'Tänään:';
				scwDrag                = 'Klikkaa tästä siirtääksesi';
				scwArrMonthNames       = ['Tammi','Helmi','Maalis','Huhti','Touko','Kesä',
										  'Heinä','Elo','Syys','Loka','Marras','Joulu'];
				scwArrWeekInits        = ['Su','Ma','Ti','Ke','To','Pe','La'];
				scwInvalidDateMsg      = 'Annettu päiväys on virheellinen.\n';
				scwOutOfRangeMsg       = 'Annettu päiväys on rajojen ulkopuolella.';
				scwDoesNotExistMsg     = 'Annettua päiväystä ei ole olemassa.';
				scwInvalidAlert        = ['Virheellistä päiväystä (',') ei huomioitu.'];
				scwDateDisablingError  = ['Virhe ',' ei ole Date objekti.'];
				scwRangeDisablingError = ['Virhe ',' pitäisi sisältää kaksi osaa.'];
                break;
				
			case 'fr':
                // French (provided by Alain Boute)
                scwToday               = 'Aujourd\'hui:';
                scwDrag                = 'D\u00E9placer le calendrier';
                scwArrMonthNames       = ['Jan','F\u00E9v','Mar','Avr','Mai','Juin',
                                          'Jui','Aou','Sep','Oct','Nov','D\u00E9c'];
                scwArrWeekInits        = ['Di','Lu','Ma','Me','Je','Ve','Sa'];
                scwInvalidDateMsg      = 'Date invalide.\n';
                scwOutOfRangeMsg       = 'Date en dehors de la plage autoris\u00E9e.';
                scwDoesNotExistMsg     = 'La date n\'existe pas.';
                scwInvalidAlert        = ['La date (',') n\'est pas reconnue (ignor\u00E9e).'];
                scwDateDisablingError  = ['Erreur ',' n\'est pas un objet Date.'];
                scwRangeDisablingError = ['Erreur ',' doit avoir deux \u00E9l\u00E9ments.'];
                break;

            case 'it':
				// Italian (provided by Fulvio Bille')
				scwToday               = 'Oggi:';
				scwDrag                = 'Click per trascinare';
				scwArrMonthNames       = ['Gen','Feb','Mar','Apr','Mag','Giu', 
										  'Lug','Ago','Set','Ott','Nov','Dic'];
				scwArrWeekInits        = ['D','L','M','M','G','V','S'];
				scwInvalidDateMsg      = 'La data inserita non è valida.\n';
				scwOutOfRangeMsg       = 'La data inserita è fuori range.';
				scwDoesNotExistMsg     = 'La data inserita non esiste.';
				scwInvalidAlert        = ['Data non valida (',') ignorata.'];
				scwDateDisablingError  = ['Errore ',' questo non è un oggetto Date.']; 
				scwRangeDisablingError = ['Errore ',' dovrebbe avere due elementi.'];			
                break;
				
            case 'nl':
                // Dutch (provided by Kees Pijnenburg, Sebastiaan Altorf and Mark de Haan)
                scwToday               = 'Vandaag:';
                scwDrag                = 'klik hier om te slepen';
                scwArrMonthNames       = ['Jan','Feb','Mar','Apr','Mei','Jun',
                                          'Jul','Aug','Sep','Okt','Nov','Dec'];
                scwArrWeekInits        = ['Z','M','D','W','D','V','Z'];
                scwInvalidDateMsg      = 'De ingevoerde datum is ongeldig.\n';
                scwOutOfRangeMsg       = 'De ingevoerde datum ligt buiten de ingestelde grenzen.';
                scwDoesNotExistMsg     = 'De ingevoerde datum bestaat niet.';
                scwInvalidAlert        = ['Ongeldige datum (',') genegeerd.'];
                scwDateDisablingError  = ['Fout ',' n\u00E3o \u00E9 is geen datum object.'];
                scwRangeDisablingError = ['Fout ',' moet uit twee elementen bestaan.'];
                break;

            case 'pl':
                // Polish (provided by Bartek Jablonski)
                scwToday               = 'Dzi\u015b:';
                scwDrag                = 'Kliknij aby przeci\u0105gn\u0105\u0107';
                scwArrMonthNames       = ['Sty','Lut','Mar','Kwi','Maj','Cze',
                                          'Lip','Sie','Wrz','Pa\u017a','Lis','Gru'];
                scwArrWeekInits        = ['N','P','W','\u015a','C','P','S'];
                scwInvalidDateMsg      = 'Podana data jest niepoprawna.\n';
                scwOutOfRangeMsg       = 'Podana data jest poza zasi\u0119giem.';
                scwDoesNotExistMsg     = 'Podana data nie istnieje.';
                scwInvalidAlert        = ['Niepoprawna data (',') zignorowana.'];
                scwDateDisablingError  = ['B\u0142\u0105d ',' nie jest obiektem typu Date.'];
                scwRangeDisablingError = ['B\u0142\u0105d ',' powinien sk\u0142ada\u0107 si\u0119 z dw\u00f3ch element\u00f3w.'];
                break;

            case 'ru':
				 //  Русский (provided by Ruslan Androsyuk)
				 scwToday               = 'Сегодня:';
				 scwDrag                = 'Кликните чтобы перетащить';
				 scwArrMonthNames       = ['Янв','Фев','Мар','Апр','Май','Июн',					
										   'Июл','Авг','Сен','Окт','Ноя','Дек'];
				 scwArrWeekInits        = ['В','П','В','С','Ч','П','С'];
				 scwInvalidDateMsg      = 'Неверная дата.\n';
				 scwOutOfRangeMsg       = 'Введенная дата вне диапазона.';
				 scwDoesNotExistMsg     = 'Введенной даты не существует.';
				 scwInvalidAlert        = ['Неверная дата (',') проигнорирована.'];
				 scwDateDisablingError  = ['Ошибка ',' не является датой.'];
				 scwRangeDisablingError = ['Ошибка ',' должна состоять из двух элементов.'];
                 break;

            case 'sv':
				// Swedish (provided by Franz Granlund)
				scwToday               = 'Idag:';
				scwDrag                = 'Klicka här för att dra';
				scwArrMonthNames       = ['Jan','Feb','Mar','Apr','Maj','Jun', 
										  'Jul','Aug','Sep','Okt','Nov','Dec'];
				scwArrWeekInits        = ['S','M','T','O','T','F','L'];
				scwInvalidDateMsg      = 'Det angivna datumet är ogiltigt.\n';
				scwOutOfRangeMsg       = 'Det angivna datumet är utanför gränserna.';
				scwDoesNotExistMsg     = 'Det angivna datumet existerar inte.';
				scwInvalidAlert        = ['Ogiltigt datum (',') ignorerat.'];
				scwDateDisablingError  = ['Fel ',' är inte ett Date objekt.']; 
				scwRangeDisablingError = ['Fel ',' borde bestå av två element.'];			
                break;
				
			default:
                // English
                scwToday               = 'Today:';
                scwDrag                = 'click here to drag';
                scwArrMonthNames       = ['Jan','Feb','Mar','Apr','May','Jun',
                                          'Jul','Aug','Sep','Oct','Nov','Dec'];
                scwArrWeekInits        = ['S','M','T','W','T','F','S'];
                scwInvalidDateMsg      = 'The entered date is invalid.\n';
                scwOutOfRangeMsg       = 'The entered date is out of range.';
                scwDoesNotExistMsg     = 'The entered date does not exist.';
                scwInvalidAlert        = ['Invalid date (',') ignored.'];
                scwDateDisablingError  = ['Error ',' is not a Date object.'];
                scwRangeDisablingError = ['Error ',' should consist of two elements.'];
            }
        }