# Partitions README

Searches through a directory organized like this:

`/mnt/partition1<br/>`
`/mnt/partition2<br/>`
`/mnt/partition3<br/>`
`...<br/>`
`/mnt/partition12`

where every file name is an unix time (i.e. elapsed milliseconds since January 1, 1970 UTC), with the suffix .data

For instance, the file
`/mnt/partition1/1478769049000.data`

represents 11/10/2016 at 9:10 AM UTC.

The program lists the content of every directory, accepts a timestamp (for example 1478769049000) and returns a path of a file whose name is lower or equal.
