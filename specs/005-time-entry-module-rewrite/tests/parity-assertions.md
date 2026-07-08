# Time Entry Parity Assertions

1. Required-field failures present equivalent validation class (`Validation errors:`).
2. Missing date does not force parsing failure and follows default-date path.
3. Non-positive hours and future date are rejected consistently.
4. Weekend behavior remains warning-oriented unless clarified.
5. Success path remains `Hours logged successfully!` with optional warning suffix.
