# Proyecto-SeCoCo

El proyecto ya se encuentra dividido en secciones, especificamente 4 secciones.
Cada sección esta representada como: Personas para los usuarios que reportan los sintomas
Diagnostico para los que envian correo de toma de prubas y resultados
Seguimiento para realizar consultas de contacos y recorridos de un usario en específico y generar alertas al perfil usuario 
Decisiones para verificar que un usuario en cuarentena si la cumpla


Para cada perfil hacen falta cosas

En el registro de la aplciación se puede ingresar ahora el perfil donde preferiblemente ingresen es: Personas, Diagnostico, Seguimiento, Decision
para identificar cual perfil van a ser

En el Main_Activity hace falta:
-Crear la manera para que en base al perfil guardado en firestore me abra un perfil en especifico(los 4 perfiles mencionados arriba)

En el perfil de Diagnostico hace falta:
-Guardar citas médicas en la base de datos - apuntando al Id del usuario al cual se le asigno (se puede realizar uso del correo porque se solicita el correo para enviar el    mensaje)
-Los resultados de la prueba tienen que ser apuntados al Id del usuario a la cual se le asigno (se puede realizar uso del correo porque se solicita el correo para enviar el mensaje)

En el perfil Usuario hace falta:
-Apuntar los sintomas al usuario en la base de datos (Usar el Id o el correo)
-Diseñar interfaz de recorridos para saber cuales recorridos ha realizado y su historial 
-Crear boton o actividad para guardar la posción (en el caso de ser botón agregarlo en la actividad de recorridos)
-Diseñar interfaz de contactos para saber los contactos que tuve con gente positiva

En el perfil de seguimientos hace falta:
-Diseñar interfaz para listar usuarios que tuvieron contacto con un caso positivo y avisarle al perfil personas de dicho contacto (enviar el resultado a la actividad de contactos_personas)

En el perfil decisiones hace falta:
-Diseñar interfaz para saber si una persona con resultado positivio de covid esta aislado o no

Lo principal sería centrarnos en el perfil de diagnostico, después continuar con seguimientos, seguir con decisiones y terminar con personas, porque en diagnostico sabemos si alguien tiene covid o no, si es positivo pasamos a seguimiento porque tenemos que conocer si tuvo contacto con otras personas, si lo tiene tenemos que pasar a desiciones para confirmar que si este en cuarentena y por ultimo enviar la alerta a personas para que sepa que tuvo contacto con un paciente covid otra persona.

Recuerden que para el miercoles tenemos que presentar Recorridos y contactos, así que centrarnos en diagnostico, seguimiento y Persona para esta entrega.

Cualquier duda por favor escribirla en el grupo de WhatsApp para colaborarnos entre todos

Por el momento si van a trabajar en un perfil, en el Main_Acitiviy esta una parte de codigo donde en comentario estan todos los perfiles, simplemente comenten el actual y descomenten el que van a utilizar, para que cuando corran el codigo solamente entren a ese perfil.
