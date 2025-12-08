# Day 3
## part 2

// second digit (1)
`for (int i = indices[0]; i < bank.length() - 10; i++) {
    int digit = bank.charAt(i) - 48;
    if (digit > digits[0]) {
        indices[1] = i;
        digits[1] = bank.charAt(i) - 48;
    }
}`

// third digit (2)
`for (int i = indices[1]; i < bank.length() - 9; i++) {
    int digit = bank.charAt(i) - 48;
    if (digit > digits[0]) {
        indices[1] = i;
        digits[1] = bank.charAt(i) - 48;
    }
}`

